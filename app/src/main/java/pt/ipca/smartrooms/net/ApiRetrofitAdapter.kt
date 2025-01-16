package pt.ipca.smartrooms.net
import android.util.Log
import okhttp3.Request
import okhttp3.ResponseBody
import okio.Timeout
import pt.ipca.smartrooms.model.SRError
import pt.ipca.smartrooms.model.Result
import retrofit2.*
import java.io.IOException
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

typealias ApiResult<T> = Result<T, SRError>

internal class ApiResultCall<S>(
    private val delegate: Call<S>,
    private val successType: Type,
    private val errorConverter: Converter<ResponseBody, SRError>
) : Call<ApiResult<S>> {

    private val TAG = "RetrofitTag"

    override fun enqueue(callback: Callback<ApiResult<S>>) {
        return delegate.enqueue(object : Callback<S> {
            override fun onResponse(call: Call<S>, response: Response<S>) {
                val body = response.body()
                val statusCode = response.code()
                val errorBody = response.errorBody()

                if (response.isSuccessful) {
                    if (body != null) {
                        callback.onResponse(
                            this@ApiResultCall,
                            Response.success(Result.Success(body))
                        )
                    } else if (successType == Unit::class.java) {
                        callback.onResponse(
                            this@ApiResultCall,
                            @Suppress("UNCHECKED_CAST")
                            Response.success(Result.Success(Unit) as ApiResult<S>)
                        )
                    }else{
                        // Response is successful but the body is null
                        callback.onResponse(
                            this@ApiResultCall,
                            Response.success(Result.Failure(SRError(statusCode, call.request().url.toString())))
                        )
                    }
                } else {
                    Log.e("API_ERROR", "$statusCode: ${errorBody?.string()}")
                    val apiProblem = when {
                            statusCode == 401 -> SRError(401, "Sem permissões para esta ação")
                            statusCode == 404 -> {
                                var message = errorBody?.string()
                                if(message.isNullOrEmpty())
                                    message = "Endereço não encontrado"
                                SRError(404, message)
                            }
                            statusCode == 403 -> SRError(403, "Proibido de executar chamada")
                            statusCode != 422 -> null
                            errorBody == null -> null
                            errorBody.contentLength() == 0L -> null
                            else -> try {
                                errorConverter.convert(errorBody)
                            } catch (ex: Exception) {
                                Log.e(TAG, ex.message, ex)
                                null
                            }
                        }
                    callback.onResponse(
                        this@ApiResultCall,
                        Response.success(Result.Failure( apiProblem ?:
                        SRError(statusCode, errorBody?.string() ?: "")))
                    )
                }
            }

            override fun onFailure(call: Call<S>, throwable: Throwable) {
                val message = throwable.message ?: "IOException:${throwable.message ?: ""}"
                val networkResponse = when (throwable) {
                    is IOException -> Result.Failure(SRError(1001, message))
                    else -> Result.Failure(SRError(1000, message))
                }
                callback.onResponse(this@ApiResultCall, Response.success(networkResponse))
            }
        })
    }

    override fun isExecuted() = delegate.isExecuted

    override fun clone() = ApiResultCall(delegate.clone(), successType, errorConverter)

    override fun isCanceled() = delegate.isCanceled

    override fun cancel() = delegate.cancel()

    override fun execute(): Response<ApiResult<S>> {
        throw UnsupportedOperationException("NetworkResponseCall doesn't support execute")
    }

    override fun request(): Request = delegate.request()

    override fun timeout(): Timeout = delegate.timeout()
}

class ApiResultCallAdapter(
    private val type: Type,
    private val errorBodyConverter: Converter<ResponseBody, SRError>
): CallAdapter<Type, Call<ApiResult<Type>>> {
    override fun responseType() = type
    override fun adapt(call: Call<Type>): Call<ApiResult<Type>> = ApiResultCall(call,type,errorBodyConverter)
}

class ApiResultCallAdapterFactory : CallAdapter.Factory() {
    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ) = when (getRawType(returnType)) {
        Call::class.java -> {
            val callType = getParameterUpperBound(0, returnType as ParameterizedType)
            when (getRawType(callType)) {
                Result::class.java -> {
                    val resultType = getParameterUpperBound(0, callType as ParameterizedType)
                    val errorBodyConverter = retrofit.nextResponseBodyConverter<SRError>(null, SRError::class.java, annotations)
                    ApiResultCallAdapter(resultType, errorBodyConverter)
                }
                else -> null
            }
        }
        else -> null
    }
}