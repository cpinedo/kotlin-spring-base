package cpp.template.baseproject.core.domain

interface Request<out T>
interface Command<out T> : Request<T>
interface Query<out T> : Request<T>