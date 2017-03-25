package hackathon.rc.ca.hackathon.client;

/**
 * Created by Boris on 2017-03-25.
 */

public class BingTextToSpeech {
    String myStringToRead;
    String languageOfVoice;
    String sexOfVoice;

    public BingTextToSpeech(String textToRead){
        this.myStringToRead = textToRead;
    }

    public BingTextToSpeech(String textToRead, String languageOfVoice, String sexOfVoice){
        this.myStringToRead = textToRead;
        this.languageOfVoice = languageOfVoice;
        this.sexOfVoice = sexOfVoice;
    }

    public void setOriginLanguage(String origin){
        languageOfVoice = origin;
    }

    public void setSexOfVoice(String sex){
        sexOfVoice = sex;
    }

    private void connectToBingAPI(){

    }

    public String getAudioURL(){
        return "0";
    }

}
