package cpp.template.baseproject.core.infrastructure.cqrs

import cpp.template.baseproject.core.domain.Command
import cpp.template.baseproject.core.domain.CommandQueryBus
import cpp.template.baseproject.core.domain.HttpStatus
import cpp.template.baseproject.core.domain.Query
import cpp.template.baseproject.core.domain.CoreRestException
import kotlin.reflect.KClass

@Suppress("UNCHECKED_CAST")
class CommandQueryBusBasic : CommandQueryBus() {


    override fun <T> dispatch(query: Query<T>): T =
        queryHandlers[query::class as KClass<Query<*>>]?.invoke(query) as T
            ?: throw HandlerNotImplementedException("No handler for query")


    override fun <T> dispatch(command: Command<T>): T =
        commandHandlers[command::class as KClass<Command<*>>]?.invoke(command) as T
            ?: throw HandlerNotImplementedException("No handler for command")

    class HandlerNotImplementedException(message: String) : CoreRestException(message, HttpStatus.NOT_IMPLEMENTED)
}