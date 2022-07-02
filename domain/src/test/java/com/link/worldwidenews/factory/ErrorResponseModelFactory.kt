
import com.link.worldwidenews.domain.model.ErrorResponseModel

object ErrorResponseModelFactory {

    fun generateDummyErrorResponseDomain(): ErrorResponseModel {
        return ErrorResponseModel(
            "code",
            "message",
            "status"
        )
    }
}