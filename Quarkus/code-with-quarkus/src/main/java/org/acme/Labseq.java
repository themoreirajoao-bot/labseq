package org.acme;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.math.BigInteger;
import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import io.quarkus.cache.CacheResult;
import io.quarkus.cache.CacheInvalidateAll;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

@Path("/labseq")
public class Labseq {

	@Operation(
			summary = "Calculates the labseq value for n",
			description = "Calculates and returns labseq value of index (n) and cache it for future calls."
	)
	@APIResponse(
			responseCode = "200",
			description = "Successful calculation"
	)
    @GET
    @Path("/{n}")
    @Produces(MediaType.APPLICATION_JSON)
	@CacheResult(cacheName = "labseq-cache")
    public Map<String, String> labseq(@PathParam("n") int n) {

		if (n < 0)
			 return Map.of("value", "null");
		if (n == 0) 
			return Map.of("value", BigInteger.ZERO.toString());
        if (n == 1) 
			return Map.of("value", BigInteger.ONE.toString());
        if (n == 2) 
			return Map.of("value", BigInteger.ZERO.toString());
        if (n == 3) 
			return Map.of("value", BigInteger.ONE.toString());

        BigInteger l0 = BigInteger.ZERO; 
        BigInteger l1 = BigInteger.ONE;  
        BigInteger l2 = BigInteger.ZERO; 
        BigInteger l3 = BigInteger.ONE;
        BigInteger labseqValue = BigInteger.ZERO;

        for (int i = 4; i <= n; i++) {
            labseqValue = l0.add(l1);  // fórmula l(n) = l(n-4) + l(n-3)

            l0 = l1;
            l1 = l2;
            l2 = l3;
            l3 = labseqValue;
        }

		return Map.of("value", labseqValue.toString());
    }
	
	@Operation(
			summary = "Reset labseq cached data",
			description = "Clears labseq-cache cached data."
	)
	@APIResponse(
			responseCode = "204",
			description = "Successful cache reset"
	)
	@POST
	@Path("/reset")
	@Produces(MediaType.APPLICATION_JSON)
	@CacheInvalidateAll(cacheName = "labseq-cache")
	public void reset() {}
}
