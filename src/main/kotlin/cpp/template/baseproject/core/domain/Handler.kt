package cpp.template.baseproject.core.domain

interface Handler<T, R : Request<T>> {
    operator fun invoke(request: R): T
}