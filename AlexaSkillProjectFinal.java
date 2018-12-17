
/**
The stream handler is the entry point for my AWS Lambda function. Every request made by an end user to 
Alexa which invokes my skill will pass through this class, into the configured Skill instance, 
and then be forwarded to the handler appropriate for the request. 
The following QuoteCentralStreamHandler creates an SDK Skill instance configured with the request handlers.
 */
package com.main;

/**
 * @author Olivia Watson-Bonthu	
 * @author Michael Gipson
 * @Date 12/3/2018
 * @Note Alexa SKill Project
 */
import com.amazon.ask.Skill;
import com.amazon.ask.SkillStreamHandler;
import com.amazon.ask.Skills;
import com.quotecentral.handlers.CancelandStopIntentHandler;
import com.quotecentral.handlers.QuoteCentralIntentHandler;
import com.quotecentral.handlers.HelpIntentHandler;
import com.quotecentral.handlers.LaunchRequestHandler;
import com.quotecentral.handlers.SessionEndedRequestHandler;

public class QuoteCentralStreamHandler extends SkillStreamHandler {
	//Data Encapsulation
	/**
	 * Quote Central Skill ID
	 */
	protected static String amazonSkillID = "amzn1.ask.skill.20c4816b-39f0-47f9-841c-fc18ee0b6d76"; 
/**
 * The getSkill method creates an SDK instance using the Skills.standard builder. We create instances of our
 * request handlers and register them with our skill with the addRequestHandlers builder method. 
 * @return Skill
 */
 private static Skill getSkill() {
        return Skills.standard()
                .addRequestHandlers(
                        new CancelandStopIntentHandler(), 
                        new QuoteCentralIntentHandler(),
                        new HelpIntentHandler(),
                        new LaunchRequestHandler(),
                        new SessionEndedRequestHandler())
                .withSkillId(amazonSkillID) //contains Quote Central skill id 
                .build();
    }

 /**
  * QuoteCentralStreamHandler constructor passes the constructed Skill instance to the constructor 
  * for the superclass SkillStreamHandler
  */
public QuoteCentralStreamHandler() {
        super(getSkill());
    }

}


package com.quotecentral.handlers;

/**
 * @author Olivia Watson-Bonthu	
 * @author Michael Gipson
 * @Date 12/3/2018
 * @Note Alexa SKill Project
 */
import java.util.Optional;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import static com.amazon.ask.request.Predicates.intentName;
import com.amazon.ask.model.Response;

/**
 * The CancelandStopIntentHandler class responds to two different intents, Amazon.CancelIntent and Amazon.StopIntent.
 */
public class CancelandStopIntentHandler implements RequestHandler {
	//Data Encapsulation
	/**
	 * the intent name
	 */
	protected String skillIntentName1 = "AMAZON.StopIntent";
	/**
	 * the intent name
	 */
	protected String skillIntentName2 = "AMAZON.CancelIntent";
	/**
	 *Speech for the response for the stop and cancel intent
	 */
	protected String speechText = "Thank you for trying the quote central skill, GoodBye."; 
	
/**
 * canHandle method detects if the incoming request is an IntentRequest
 * <p> returns true if the intent name is "AMAZON.StopIntent" or "AMAZON.CancelIntent".
 * A response is then generated and returned.
 * @param input
 * @return boolean intent name matches
 */
 public boolean canHandle(HandlerInput input) {
        return input.matches(intentName(skillIntentName1).or(intentName(skillIntentName2)));
    }
 /**
  *handle method sends the help or stop directions to ALEXA.
  *@param input
  *@return input
  */
public Optional<Response> handle(HandlerInput input) {
             return input.getResponseBuilder()
                .withSpeech(speechText)  //our speech for our response
                .withSimpleCard("QuoteCentral", speechText)  //Sends back title and body
                .build();
    }

}

 
package com.quotecentral.handlers;

/**
 * @author Olivia Watson-Bonthu	
 * @author Michael Gipson
 * @Date 12/3/2018
 * @Note Alexa SKill Project
 */
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;
import java.util.Optional;
import static com.amazon.ask.request.Predicates.intentName;

/**
 * THe FallbackIntentHandler helps the user if the user
 * provides utterances that do no match any of our skills intents .
 */
public class FallbackIntentHandler implements RequestHandler {
	//Data Encapsulation
	/**
	 * Response speech for fallback intent
	 */
	protected String speechText = "Sorry, I don't know that. You can say try saying help!";
	/**
	 * the intent name
	 */
	protected String skillIntentName = "AMAZON.FallbackIntent";
/**
 * canHandle method checks whether that particular handler can handle that request.
 * @param input
 * @return boolean
 */
	public boolean canHandle(HandlerInput input) {
        return input.matches(intentName(skillIntentName));
    }
/**
*handle method sends the directions to ALEXA for fallback intent.
*@param input
*@return input
*/
	public Optional<Response> handle(HandlerInput input) {
         return input.getResponseBuilder()
                .withSpeech(speechText) //our speech for our response
                .withSimpleCard("QuoteCentral", speechText) //Sends back title and body
                .withReprompt(speechText) //our reprompt speech sent to user
                .build();
    }

}



package com.quotecentral.handlers;

/**
 * @author Olivia Watson-Bonthu	
 * @author Michael Gipson
 * @Date 12/3/2018
 * @Note Alexa SKill Project
 */
import java.util.Optional;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import static com.amazon.ask.request.Predicates.intentName;
import com.amazon.ask.model.Response;

/**
 * THe HelpIntentHandler class gives a response to the user if they need help or if a response is needed
 * from the program to the user.
 */
public class HelpIntentHandler implements RequestHandler {
	//Data Encapsulation
	/**
	 * This is the intent name.
	 */
	protected String skillIntentName = "AMAZON.HelpIntent";
	/**
	 * This will be the response speech for the HelpIntent when user needs it.
	 */
	protected String speechText = "You can say quote teller, tell me a quote or, quote teller, get me a quote!";
/**
 * canHandle method checks whether that particular handler is the correct handler.
 * @param input
 * @return boolean true if the correct handler
 */
 public boolean canHandle(HandlerInput input) {
        return input.matches(intentName(skillIntentName));
    }
 /**
 *handle method sends the directions to ALEXA for help intent.
 *@param input
 *@return input along with Simplecard that displays plain text.
 */
public Optional<Response> handle(HandlerInput input) {
		return input.getResponseBuilder()
                .withSpeech(speechText) //our speech for our response
                .withSimpleCard("QuoteCentral", speechText) //Sends back title and body
                .withReprompt(speechText) //our reprompt speech sent to user
                .build();
    }

}


package com.quotecentral.handlers;

/**
 * @author Olivia Watson-Bonthu	
 * @author Michael Gipson
 * @Date 12/3/2018
 * @Note Alexa SKill Project
 */
import java.util.Optional;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.LaunchRequest;
import com.amazon.ask.model.Response;
import static com.amazon.ask.request.Predicates.requestType;

/**
 * The LaunchRequestHandler class is invoked when the skill receives a LaunchRequest. 
 * The LaunchRequest event occurs when the skill is invoked without a specific intent.
 */
public class LaunchRequestHandler implements RequestHandler  {
	//Data Encapsulation
	/**
	 * This will be the response speech for the LaunchRequestHandler when user needs it.
	 */
	protected String speechText = "Welcome to the Quote Central Skill! You can say, quote teller, tell me a quote, or give me quote.";
	
/**
* canHandle method checks whether that particular handler is the correct handler.
* @param input
* @return boolean true if the correct handler
*/
 public boolean canHandle(HandlerInput input) {
        return input.matches(requestType(LaunchRequest.class));
    }
 /**
 *handle method sends the directions to ALEXA for user.
 *@param input
 *@return input along with Simplecard that displays plain text.
 */
public Optional<Response> handle(HandlerInput input) {
	    return input.getResponseBuilder()
                .withSpeech(speechText) //our speech for our response
                .withSimpleCard("QuoteCentral", speechText)  //Sends back title and body               
                .withReprompt(speechText) //our reprompt speech sent to user
                .build();
    }

}


package com.quotecentral.handlers;

/**
 * @author Olivia Watson-Bonthu	
 * @author Michael Gipson
 * @Date 12/3/2018
 * @Note Alexa SKill Project
 */

import java.util.Optional;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;
import static com.amazon.ask.request.Predicates.intentName;
/**
 * The QuoteCentralIntentHandler gets a random quote and returns to the user.
 */
public class QuoteCentralIntentHandler implements RequestHandler {
	//Data Encapsulation
	/**
	 * holds the random quote sent for the skill
	 */
	protected String quote;
	/**
	 * intent name
	 */
	protected String skillIntentName = "QuoteCentralIntent";
	/**
	 * speech response
	 */
	protected String speechText = "Here's your quote.";
	/**
	 * audio file according to Alexa SKills standard
	 */
	protected String audioFile1 = "<audio src ='https://s3.amazonaws.com/silver-giggle-bucket/Bpm095_F%23m_SpaceWasteland_Pad_firstfile.mp3'/>";
	/**
	 * String [] that contains quotes for our skill with different voices, attached short clip audio using SSML tags.
	 */
	protected final String[] COOL_QUOTES = new String[]{
			
		"<voice name = \"Joey\"> Just because something doesn’t do what you planned it to do, doesn’t mean it’s useless. By Thomas Edison.</voice>"+
		(audioFile1),
		
    	" <voice name = \"Matthew\"> One machine can do the work of fifty ordinary men." +
    	" No machine can do the work of one extraordinary man. By Elbert Hubbard.</voice>"+
    	(audioFile1),
    	
    	"<voice name = \"Brian\"> Tell me and I forget. Teach me and I remember. Involve me and I learn. By Benjamin Franklin.</voice>"+
    	(audioFile1),
    	
    	"<voice name = \"Matthew\"> It does not matter how slowly you go as long as you do not stop. By Confucius.</voice>" + 
    	(audioFile1),
    	
    	"<voice name = \"Kendra\"> Set your goals high, and dont stop till you get there. By Bo Jackson.</voice>" +
    	(audioFile1),
    	
    	"<voice name = \"Joey\"> Our greatest weakness lies in giving up. The most certain way to succeed is always to " +
    	"try just one more time. By Thomas Edison.</voice>" +
    	(audioFile1),	
    	
    	"<voice name = \"Matthew\"> If you can dream it, you can do it. By Walt Disney.</voice>" +
    	(audioFile1),
    	
    	"<voice name = \"Brian\"> You cannot cross the sea merely by standing and staring at the water. By Rabindranath Tagore.</voice>" +
    	(audioFile1),
    	
    	"<voice name = \"Joey\"> If you want to conquer fear, dont sit at home and think about it. Go out and get busy. By Dale Carnegie.</voice>" +
    	(audioFile1),
    	
    	"<voice name = \"Matthew\"> The secret of getting ahead is getting started. By Mark Twain.</voice>" +
    	(audioFile1),
    	
    	"<voice name = \"Brian\"> Without hard work, nothing grows but weeds. By Gordon B. Hinckley.</voice>" +
    	(audioFile1),
    	
    	"<voice name = \"Joey\"> Quality is not an act, it is a habit. By Aristotle.</voice>" +
    	(audioFile1),
    	
    	"<voice name = \"Matthew\"> Start where you are. Use what you have. Do what you can. By Arthur Ashe.</voice>" +
    	(audioFile1),
    			
    	"<voice name = \"Brian\"> What you do today can improve all your tomorrows. By Ralph Marston.</voice>" +
    	(audioFile1),
    	
    	"<voice name = \"Joey\"> Dont watch the Clock. Do what it does. Keep going. By Sam Levenson.</voice>" +
    	(audioFile1),
    	
    	"<voice name = \"Matthew\"> Aim for the moon. If you miss, you may hit a star. By W. Clement Stone.</voice>" +
    	(audioFile1)
    	};

	
/**
 * The canHandle method detects if the incoming request is an IntentRequest, 
 * and returns true if the intent name is QuoteCentralIntent.
 * @param input
 * @return boolean true if the correct handler
 */
 public boolean canHandle(HandlerInput input) {
        return input.matches(intentName(skillIntentName));
       }
 /**
  *handle method sends random quote response.
  *@param input
  *@return input
  */
 public Optional<Response> handle(HandlerInput input) {
		speechText += getQuote();
			    return input.getResponseBuilder()
                .withSpeech(speechText) //our speech for our response
                .withSimpleCard("QuoteCentral", speechText) //Sends back title and body
                .build();
 		}
/**
 * getQuote method generates a random quote from the String[] COOL_QUOTES.
 * @return String random quote
 */
 public String getQuote() {
	 		//Generate a random quote to send back as our QuoteCentralIntent response
			int quoteIndex = (int) Math.floor(Math.random() * COOL_QUOTES.length);
			this.quote = COOL_QUOTES[quoteIndex];
			return quote;
 		}

}


package com.quotecentral.handlers;

/**
 * @author Olivia Watson-Bonthu	
 * @author Michael Gipson
 * @Date 12/3/2018
 * @Note Alexa SKill Project
 */
import java.util.Optional;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.SessionEndedRequest;
import static com.amazon.ask.request.Predicates.requestType;

/**
 * SessionEndedRequestHandler class
 * handles the session ending when the user makes that request. 
 */
public class SessionEndedRequestHandler implements RequestHandler  {
/**
 * canHandle method checks whether that particular handler handles that request.
 * @param input 
 * @return boolean true id handler matches request type
 */
 public boolean canHandle(HandlerInput input) {
        return input.matches(requestType(SessionEndedRequest.class));
    }
 /**
  *handle method handles session ended.
  *@param input
  *@return nothing is sent back to user. It just handles that the session is ended.
  */
public Optional<Response> handle(HandlerInput input) {
        return input.getResponseBuilder().build();
    }

}




