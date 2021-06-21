package console.objects.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import data.model.Location;

public class ClientSideApp {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		
		System.out.println("ClientSideApp: Demo to process a list of objects on TCP \n");

		// Request data
		Location location1 = new Location();
		location1.setName("Programming Lab 5");
		
		Location location2 = new Location();
		location2.setName("Multimedia Studio");
		
		Location location3 = new Location();
		location3.setName("Research Lab");
		
		// Add into list
		List<Location> locations = new ArrayList<Location>();
		locations.add(location3);
		locations.add(location1);
		locations.add(location2);

		try {

			// Data to establish connection to server
			int portNo = 4228;
			InetAddress serverAddress = InetAddress.getLocalHost();

			// Connect to the server at localhost, port 4228
			Socket socket = new Socket(serverAddress, portNo);

			// Open stream to send object
			ObjectOutputStream objectOS = new ObjectOutputStream(socket.getOutputStream());

			// Send request to server
			System.out.println("Send object to server: " + locations);
			objectOS.writeObject(locations);
			objectOS.flush();
			
			// Open stream to receive object
			ObjectInputStream objectIS = new ObjectInputStream(socket.getInputStream());
			
			// Get object from stream, cast and display details
			locations = (ArrayList<Location>) objectIS.readObject();
			for (Location location:locations)
				System.out.println ("Id for " + location.getName() + " is " + location.getLocationId());
			
			// Close objects
			objectOS.close();
			objectIS.close();
			socket.close();

		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("\nClientSideApp: End of application.\n");

	}

}
