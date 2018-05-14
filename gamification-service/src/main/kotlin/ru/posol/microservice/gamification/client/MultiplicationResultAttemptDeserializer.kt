package ru.posol.microservice.gamification.client

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonNode
import ru.posol.microservice.gamification.client.dto.MultiplicationResultAttempt


/**
 * Deserializes an attempt coming from the Multiplication microservice
 * into the Gamification's representation of an attempt.
 */
class MultiplicationResultAttemptDeserializer : JsonDeserializer<MultiplicationResultAttempt>() {

    override fun deserialize(jsonParser: JsonParser,
                             deserializationContext: DeserializationContext): MultiplicationResultAttempt {
        val oc = jsonParser.getCodec()
        val node: JsonNode = oc.readTree(jsonParser)
        return MultiplicationResultAttempt(node.get("user").get("alias").asText(),
                node.get("multiplication").get("factorA").asInt(),
                node.get("multiplication").get("factorB").asInt(),
                node.get("resultAttempt").asInt(),
                node.get("correct").asBoolean())
    }
}