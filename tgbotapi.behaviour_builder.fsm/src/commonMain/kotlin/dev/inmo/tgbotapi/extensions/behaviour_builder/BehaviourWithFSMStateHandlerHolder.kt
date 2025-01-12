package dev.inmo.tgbotapi.extensions.behaviour_builder

import dev.inmo.micro_utils.coroutines.LinkedSupervisorScope
import dev.inmo.micro_utils.coroutines.weakLaunch
import dev.inmo.micro_utils.fsm.common.*
import dev.inmo.tgbotapi.types.update.abstracts.Update
import dev.inmo.tgbotapi.updateshandlers.FlowsUpdatesFilter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlin.reflect.KClass

/**
 * Special holder for [BehaviourContextWithFSM]. This holder helps [BehaviourContextWithFSM] to understand whether it
 * can handle input [State] with [delegateTo] or not
 *
 * @param inputKlass This [KClass] will be used to compare input [State] type and declare ability of [delegateTo] to
 * handle incoming [State]. See [checkHandleable] for more info
 * @param strict This flag will be used in [checkHandleable] to choose strategy of checking incoming [State]
 * @param delegateTo This handler will be called in case [checkHandleable] returns true with class caster incoming
 * [State] in [handleState]
 */
class BehaviourWithFSMStateHandlerHolder<I : O, O : State>(
    private val inputKlass: KClass<I>,
    private val strict: Boolean = false,
    private val delegateTo: BehaviourWithFSMStateHandler<I, O>
) {
    /**
     * Check ability of [delegateTo] to handle this [state]
     *
     * @return When [state]::class exactly equals to [inputKlass] will always return true. Otherwise when [strict]
     * mode is disabled, will be used [KClass.isInstance] of [inputKlass] for checking
     */
    fun checkHandleable(state: O): Boolean = state::class == inputKlass || (!strict && inputKlass.isInstance(state))

    /**
     * Handling of state :)
     *
     * @param contextUpdatesFlow This [Flow] will be used as source of updates. By contract, this [Flow] must be common
     * for all [State]s of incoming [state] [State.context] and for the whole chain inside of [BehaviourContextWithFSM]
     */
    suspend fun BehaviourContextWithFSM<in O>.handleState(
        contextUpdatesFlow: Flow<Update>,
        state: O
    ): O? {
        val subscope = scope.LinkedSupervisorScope()
        return with(copy(scope = subscope, upstreamUpdatesFlow = contextUpdatesFlow)) {
            with(delegateTo) {
                handleState(state as I)
            }
        }
    }
}

inline fun <reified I : O, O : State> BehaviourWithFSMStateHandlerHolder(
    strict: Boolean = false,
    delegateTo: BehaviourWithFSMStateHandler<I, O>
) = BehaviourWithFSMStateHandlerHolder(I::class, strict, delegateTo)
