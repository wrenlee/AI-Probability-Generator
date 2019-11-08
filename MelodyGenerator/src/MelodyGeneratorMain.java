
//Wren Lee
//Main class
//Adapted from Dr. Brown's code
//Sept 10

 /*
 * Class: H
 * Description: Demonstration of MIDI file manipulations, etc. & 'MelodyPlayer' sequencer
 * 
 */

import processing.core.*;

import java.util.*; 

//importing the JMusic stuff
import jm.music.data.*;
import jm.JMC;
import jm.util.*;
import jm.midi.*;

import java.io.UnsupportedEncodingException;
import java.net.*;

//import javax.sound.midi.*;

			//make sure this class name matches your file name, if not fix.
public class MelodyGeneratorMain extends PApplet {
	boolean isPlay;

	MelodyPlayer player; //play a midi sequence
	MidiFileToNotes midiNotes; //read a midi file
	ProbabilityGenerator rhythm; //calls test for rhythms
	ProbabilityGenerator pitch; //calls test for pitches

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PApplet.main("MelodyGeneratorMain"); //change this to match above class & file name 

	}

	//setting the window size to 300x300
	public void settings() {
		size(500, 500);
	}

	//doing all the setup stuff
	public void setup() {
		fill(120, 50, 240);
		background(100);

		// returns a url
		String filePath = getPath("mid/MaryHadALittleLamb.mid"); //test file
		//String filePath = getPath("mid/toto-africa.mid");
		 //playMidiFile(filePath);

		midiNotes = new MidiFileToNotes(filePath); //creates a new MidiFileToNotes -- reminder -- ALL objects in Java must 
													//be created with "new". Note how every object is a pointer or reference. Every. single. one.


//		// which line to read in --> this object only reads one line (or ie, voice or ie, one instrument)'s worth of data from the file
		midiNotes.setWhichLine(0);

		player = new MelodyPlayer(this, 100.0f);
		
		//initialize pitch and rhythm generic classes 
		pitch = new ProbabilityGenerator();
		rhythm = new ProbabilityGenerator();
		
		System.out.println("PITCH");
		System.out.println(midiNotes.getPitchArray());

		pitch.train(midiNotes.getPitchArray());
		pitch.train(midiNotes.getPitchArray());

		pitch.train(midiNotes.getPitchArray());

		System.out.println("RHYMTH");
		rhythm.train(midiNotes.getRhythmArray());

		player.setup();
		player.setMelody(pitch.generateMultiple(20));
		player.setRhythm(rhythm.generateMultiple(20));
		
		fill(255);
		text("Press s to start, space to stop, 1 to play test 1, 2 to play test 2, and 3 to play test 3", 10, height/2);
	}

	public void draw() {
		if(isPlay) {
			player.play(); //play each note in the sequence -- the player will determine whether is time for a note onset
		}//is play true
	}//draw

	//this finds the absolute path of a file
	String getPath(String path) {

		String filePath = "";
		try {
			filePath = URLDecoder.decode(getClass().getResource(path).getPath(), "UTF-8");

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return filePath;
	}

	//this function is not currently called. you may call this from setup() if you want to test
	//this just plays the midi file -- all of it via your software synth. You will not use this function in upcoming projects
	//but it could be a good debug tool.
	void playMidiFile(String filename) {
		Score theScore = new Score("Temporary score");
		Read.midi(theScore, filename);
		Play.midi(theScore);
	}
	
	public void unitTest3(ArrayList<Integer> song) {
		
	ProbabilityGenerator<Integer> melodyGen = new ProbabilityGenerator();
	ProbabilityGenerator<Integer> probDistGen = new ProbabilityGenerator();
	
	melodyGen.train(song);
	for(int i=1; i< 10000; i++)
	{
		ArrayList<Integer> newSong = melodyGen.generateMultiple(20); //generates 20 notes
		probDistGen.train(newSong);
	}
	probDistGen.unitTest1();
	//System.out.println("Test 3");
}//unit test 3
	
	public void unitTest3Rhythms(ArrayList<Double> song) {
		
		ProbabilityGenerator<Double> melodyGen = new ProbabilityGenerator();
		ProbabilityGenerator<Double> probDistGen = new ProbabilityGenerator();
		
		melodyGen.train(song);
		for(int i=1; i< 10000; i++)
		{
			ArrayList<Double> newSong = melodyGen.generateMultiple(20); //generates 20 notes
			probDistGen.train(newSong);
		}
		
	probDistGen.unitTest1();
	}//unit test three: generate 20 long melodies 10,000 times and println each melody
	
	
	//this starts & restarts the melody.
	public void keyPressed() {
		if(key == 's') {
			isPlay = true;
			text("Player has started", 10, height/2 + 30);
		}//key s to start
		if(key == ' ') {
			isPlay = false;
			text("Player has stopped", 10, height/2 + 60);
		}//space to stop
		if(key == '1') {
			System.out.println("Test 1");
			System.out.println("Rhythm");
			rhythm.unitTest1();
			System.out.println("Pitches");
			pitch.unitTest1();
		}//1 to unit test 1
		if(key == '2') {
			System.out.println("Test 2");
			rhythm.unitTest2();
			pitch.unitTest2();
		}//2 to unit test 2
		if(key == '3') {
			unitTest3Rhythms(midiNotes.getRhythmArray());
			unitTest3(midiNotes.getPitchArray());
		}//3 to unit test 3
	}//key press
	
}//ends main
