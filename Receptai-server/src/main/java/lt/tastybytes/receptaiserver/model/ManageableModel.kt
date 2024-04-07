package lt.tastybytes.receptaiserver.model

import lt.tastybytes.receptaiserver.exception.MissingRightsException
import lt.tastybytes.receptaiserver.model.user.User

interface ManageableModel {
    /**
     * Asserts that the model can be managed by the specified user.
     *
     * Throws MissingRightsException on failure.
     */
    @Throws(MissingRightsException::class)
    fun assertCanBeManagedBy(user: User)
}