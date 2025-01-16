package pt.ipca.smartrooms.model

sealed class Result<out T,out E> {
    data class Success<T>(val data: T) : Result<T,Nothing>()
    data class Failure<E>(val error: E): Result<Nothing,E>()
}

// From: https://github.com/michaelbull/kotlin-result

/**
 * Maps this [Result<V, E>][Result] to [Result<U, E>][Result] by either applying the [transform]
 * function if this [Result] is [Ok], or returning this [Err].
 *
 * - Elm: [Result.andThen](http://package.elm-lang.org/packages/elm-lang/core/latest/Result#andThen)
 * - Rust: [Result.and_then](https://doc.rust-lang.org/std/result/enum.Result.html#method.and_then)
 */

inline infix fun <V, E, U> Result<V, E>.andThen(transform: (V) -> Result<U, E>): Result<U, E> {
    return when (this) {
        is Result.Success -> transform(data)
        is Result.Failure -> this
    }
}

/**
 * Maps this [Result<V, E>][Result] to [Result<U, E>][Result] by either applying the [transform]
 * function to the [value][Ok.value] if this [Result] is [Ok], or returning this [Err].
 *
 * - Elm: [Result.map](http://package.elm-lang.org/packages/elm-lang/core/latest/Result#map)
 * - Haskell: [Data.Bifunctor.first](https://hackage.haskell.org/package/base-4.10.0.0/docs/Data-Bifunctor.html#v:first)
 * - Rust: [Result.map](https://doc.rust-lang.org/std/result/enum.Result.html#method.map)
 */

inline infix fun <V, E, U> Result<V, E>.map(transform: (V) -> U): Result<U, E> {
    return when (this) {
        is Result.Success -> Result.Success(transform(data))
        is Result.Failure -> this
    }
}

/**
 * Returns the [value][Ok.value] if this [Result] is [Ok], otherwise `null`.
 *
 * - Elm: [Result.toMaybe](http://package.elm-lang.org/packages/elm-lang/core/latest/Result#toMaybe)
 * - Rust: [Result.ok](https://doc.rust-lang.org/std/result/enum.Result.html#method.ok)
 */
fun <V, E> Result<V, E>.get(): V? {

    return when (this) {
        is Result.Success -> data
        is Result.Failure  -> null
    }
}


/**
 * Returns the [value][Ok.value] if this [Result] is [Ok], otherwise [default].
 *
 * - Elm: [Result.withDefault](http://package.elm-lang.org/packages/elm-lang/core/latest/Result#withDefault)
 * - Haskell: [Result.fromLeft](https://hackage.haskell.org/package/base-4.10.0.0/docs/Data-Either.html#v:fromLeft)
 * - Rust: [Result.unwrap_or](https://doc.rust-lang.org/std/result/enum.Result.html#method.unwrap_or)
 *
 * @param default The value to return if [Err].
 * @return The [value][Ok.value] if [Ok], otherwise [default].
 */
infix fun <V, E> Result<V, E>.getOr(default: V): V {
    return when (this) {
        is Result.Success -> data
        is Result.Failure -> default
    }
}