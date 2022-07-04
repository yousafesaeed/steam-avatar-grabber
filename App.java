import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class App {
public static void main(String[] args) throws IOException {

	// prints out info for program
	System.out.println("Steam Avatar Grabber - By: Yousef Saeed");
	System.out.println("github.com/yousafesaeed");

	while(true){
		// promotes the user to input the steam profile URL
		Scanner URL = new Scanner(System.in);
		System.out.println("Enter the profile URL :");
		String url = URL.nextLine();

		// grab the avatar URL using it's tag in the head
		/* didn't use the tag in the body, so it doesn't get messed up
		in case if the profile has Avatar Frame, while keeping the code simple */
		Document doc = Jsoup.connect(url).get();
		String img = doc.head().select("meta[property=og:image]").get(0).attr("content");
		
		// renaming the image using RNG, so they don't override each other
		double rng = Math.random();
		String destinationFile = "Avater-" +rng+ ".jpg";
		saveImage(img, destinationFile);

		System.out.println("All Done!");

		// promotes the user if want to download another image
		System.out.println("Another One?!! -  Yes = (Y,y) / No = (N,n)");	

		Scanner decision = new Scanner(System.in);
		String answer = decision.nextLine();

		// checks for user input and handles exceptions
		try {
			if (answer.equalsIgnoreCase("Y")){
				continue;
			}
			else if (answer.equalsIgnoreCase("N")) {
				break;
			}
			else {
				System.out.println("You didn't make a proper selection");
				break;
			}
		}
		catch(Exception e) {
			System.out.println("Something went wrong");
			System.exit(0);
		}


		URL.close();
		decision.close();
	}
}


// the method to download and save the image
public static void saveImage(String imageUrl, String destinationFile) throws IOException {

	URL url = new URL(imageUrl);
	InputStream is = url.openStream();
	OutputStream os = new FileOutputStream(destinationFile);

	byte[] b = new byte[2048];
	int length;

	while ((length = is.read(b)) != -1) {
		os.write(b, 0, length);
	}

	is.close();
	os.close();
}

}
