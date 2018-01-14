package eu.righettod.pocidor.util;

import javax.xml.bind.DatatypeConverter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Handle the creation of ID that will be send to front end side in order to prevent IDOR
 */
public class IDORUtil {

    /**
     * SALT used for the generation of the HASH of the real item identifier in order to prevent to forge it on front end side.
     * <p>
     * This one is static here for the POC but it should be set using configuration properties in order to handle app load balancing topology.
     */
    private static final String SALT = "B9H^ySBegSW&wErKgF84D6T#c_^Fx#";

    /**
     * Compute a identifier that will be send to the front end and be used as item unique identifier on client side.
     *
     * @param realItemBackendIdentifier Identifier of the item on the backend storage (real identifier)
     * @return A string representing the identifier to use
     * @throws UnsupportedEncodingException If string's byte cannot be obtained
     * @throws NoSuchAlgorithmException If the hashing algorithm used is not supported is not available
     */
    public static String computeFrontEndIdentifier(String realItemBackendIdentifier) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        String frontEndId = null;
        if (realItemBackendIdentifier != null && !realItemBackendIdentifier.trim().isEmpty()) {
            //Prefix the value with the SALT
            String tmp = SALT + realItemBackendIdentifier;
            //Get and configure message digester
            //We use SHA1 here for the following reason even if SHA1 have now potential collision:
            //1. We do not store sensitive information, just technical ID
            //2. We want that the ID stay short but not guessable
            //3. We want that a maximum of backend storage support the algorithm used in order to compute it in selection query/request
            //If your backend storage supports SHA256 so use it instead of SHA1
            MessageDigest digester = MessageDigest.getInstance("sha1");
            //Compute the hash
            byte[] hash = digester.digest(tmp.getBytes("utf-8"));
            //Encode is in HEX
            frontEndId = DatatypeConverter.printHexBinary(hash);

        }
        return frontEndId;


    }

}
