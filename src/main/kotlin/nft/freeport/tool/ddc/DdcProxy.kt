package nft.freeport.tool.ddc

import org.jboss.resteasy.reactive.RestHeader
import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@Path("")
interface DdcProxy {
    @GET
    @Path("auth/v1/{address}/nonce")
    @Produces(MediaType.TEXT_PLAIN)
    fun getNonce(address: String): String

    @POST
    @Path("auth/v1/{address}")
    fun authenticate(address: String, rq: AuthRequest): AuthToken

    @GET
    @Path("assets/v2/presigned-urls")
    fun generatePutPresignedUrls(@RestHeader authorization: String): PresignedUrls

    @POST
    @Path("assets/v2/{assetKey}")
    @Produces(MediaType.TEXT_PLAIN)
    fun upload(assetKey: String, form: LargeAssetUploadForm, @RestHeader authorization: String): String
}
