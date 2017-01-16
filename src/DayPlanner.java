import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/** DayPlanner class
 *
 * Name: Lucas Boyd
 * Student ID# 0795339
 * Assignment 3.
 * 
 * I used the example code given out for assignment 2 when implementing the
 * GUI. the example code was written by fay song.
 * 
 *
 */




public class DayPlanner extends JFrame{
        private final JPanel startPanel;
        private final JPanel buttonPanel;
        private final JPanel addPanel;
        private final JPanel searchPanel;
        private final JPanel messageBox; 
        private final JPanel infoArea;
        private final JPanel topAdd;
        private final JMenuBar bar;
        private final JMenu commandMenu;
        private final JLabel locationLabel = new JLabel("<html><br>Location: <html>");
        private final JTextField locationField = new JTextField();
        private final JTextField titleField = new JTextField();
        private final JTextField startField = new JTextField();
        private final JTextField endField = new JTextField();
        private final JTextField commentField = new JTextField();
        private final JTextArea messageAdd = new JTextArea(15, 30);
        private final JPanel topSearch = new JPanel();
        private final JPanel searchArea = new JPanel();
        private final JPanel buttonSearch = new JPanel();
        private final JComboBox typeBox = new JComboBox();
        private final JComboBox typeBoxSrch = new JComboBox();
        private final JTextField titleFieldSearch = new JTextField();
        private final JTextField startFieldSearch = new JTextField();
        private final JTextField endFieldSearch = new JTextField();
        private final JTextArea messageSearch = new JTextArea(15, 30);
        
        public void actionPerformed(ActionEvent e) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
        
        public class commandListener implements ActionListener
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String choice = e.getActionCommand();
                int option;
                switch(choice){
                    case "Add":
                        searchPanel.setVisible(false);
                        startPanel.setVisible(false);
                        addPanel.setVisible(true);
                        break;
                    case "Search":
                        startPanel.setVisible(false);
                        searchPanel.setVisible(true);
                        addPanel.setVisible(false);
                        break;
                    case "Quit":
                        option = JOptionPane.showConfirmDialog(null, "Are you sure you would like to quit?");
                        System.out.printf("%d", option);
                        if(option == 0){
                            System.exit(0);
                        }
                        break;
                    default:
                        break;
                }
            }
        } 
        
        public class addButtonListener implements ActionListener
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String button = e.getActionCommand();
                switch(button){
                    case "Enter":
                        try{
                            Time startingTime = null;
                            Time endingTime = null;
                            String title = titleField.getText();
                            String comment = commentField.getText();
                            String location = locationField.getText();
                            String type = typeBox.getSelectedItem().toString();
                            startingTime = getTime(startField.getText());
                            endingTime = getTime(endField.getText());
                            if(startingTime!=null && endingTime!=null){
                                switch(type){
                                    case "School":
                                        addSchoolActivity(new SchoolActivity(title, startingTime, endingTime, comment));
                                        break;
                                    case "Home":
                                        addHomeActivity(new HomeActivity(title, startingTime, endingTime, comment));
                                        break;
                                    case "Other":
                                        addOtherActivity(new OtherActivity(title, startingTime, endingTime, location, comment));
                                        break;
                                }
                            }
                            else{
                                JOptionPane.showMessageDialog(null, "Error! you need to enter a starting and ending time!");
                            }
                            
                        }
                        catch(Exception a){
                            JOptionPane.showMessageDialog(null, "Error! One of the fields either had nothing in it, or was fild out incorrectly");
                        }
                        break;
                    case "Reset":
                        titleField.setText("");
                        locationField.setText("");
                        startField.setText("");
                        endField.setText("");
                        commentField.setText("");
                        break;
                }
            }
        } 
        
        public class searchButtonListener implements ActionListener
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String button = e.getActionCommand();
                System.out.printf("%s", button);
                switch(button){
                    case "Enter":
                        String title = titleFieldSearch.getText();
                        String type = typeBoxSrch.getSelectedItem().toString();
                        Time startingTime = getTime(startFieldSearch.getText());
                        Time endingTime = getTime(endFieldSearch.getText());
                        
                        String[] keywords = null;
                        if (!title.isEmpty())
                        keywords = title.split("[ ,\n]+");

                       type = typeBoxSrch.getSelectedItem().toString();
                        
                        if(type.equals("School") || type.equals(" ")){
                            for (int i = 0; i < schoolsSize; i++) 
                                if ((startingTime == null || schools[i].getStartingTime().compareTo(startingTime) >= 0) &&
                                (endingTime == null || schools[i].getEndingTime().compareTo(endingTime) <= 0) &&
                                (keywords == null || matchedKeywords(keywords, schools[i].getTitle())))
                                    messageSearch.setText(messageSearch.getText()+"\n"+schools[i].toString());
                        }
                        else if(type.equals("Home") || type.equals(" ")){
                            for (int i = 0; i < homesSize; i++) 
			if ((startingTime == null || homes[i].getStartingTime().compareTo(startingTime) >= 0) &&
			    (endingTime == null || homes[i].getEndingTime().compareTo(endingTime) <= 0) &&
			    (keywords == null || matchedKeywords(keywords, homes[i].getTitle())))
				messageSearch.setText(messageSearch.getText()+"\n"+homes[i].toString()); 		
                        }
                        else{
                         for (int i = 0; i < othersSize; i++) 
                            if ((startingTime == null || others[i].getStartingTime().compareTo(startingTime) >= 0) &&
                                (endingTime == null || others[i].getEndingTime().compareTo(endingTime) <= 0) &&
                                (keywords == null || matchedKeywords(keywords, others[i].getTitle())))
				messageSearch.setText(messageSearch.getText()+"\n"+others[i].toString()); 		   
                        }
                        break;
                    case "Reset":
                        titleFieldSearch.setText("");
                        startFieldSearch.setText("");
                        endFieldSearch.setText("");
                        break;
                }
            }
        } 
        
        private class typeListener implements ActionListener{

       
            @Override
            public void actionPerformed(ActionEvent e) {
               String choice = typeBox.getSelectedItem().toString();
               switch(choice){
                    case "School":
                        locationLabel.setVisible(false);
                        locationField.setVisible(false);
                            break;
                    case "Home":
                        locationLabel.setVisible(false);
                        locationField.setVisible(false);
                        break;
                    case "Other":
                        locationLabel.setVisible(true);
                        locationField.setVisible(true);
                        break;
               }
            } 
            
        }
            
            /******************************************************************/
        
    
	public static final int MAX_SIZE = 5;

	public static final String[] types = new String[]{"home", "school", "other", "h", "s", "o"};

	/**
	 * Three separate lists for home, school, and other activities and their actual sizes
	 */
	private HomeActivity[] homes = new HomeActivity[MAX_SIZE];
	private SchoolActivity[] schools = new SchoolActivity[MAX_SIZE];
	private OtherActivity[] others = new OtherActivity[MAX_SIZE];
	private int homesSize; 
	private int schoolsSize; 
	private int othersSize; 
	
    /**
     *
     */
    public DayPlanner() {
            super("Day Planner");
            homes = new HomeActivity[MAX_SIZE];
            schools = new SchoolActivity[MAX_SIZE];
            others = new OtherActivity[MAX_SIZE];
            homesSize = 0;
            schoolsSize = 0;
            othersSize = 0;
            startPanel = new JPanel();
            buttonPanel = new JPanel();
            topAdd = new JPanel();
            addPanel= new JPanel();
            searchPanel= new JPanel();
            messageBox= new JPanel();
            infoArea= new JPanel();
      
            /*Initial Screen*/    
            setSize(1000,600);
            setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            
            /*Command menu/startmenu*/
            commandMenu = new JMenu("Command");
            JMenuItem add = new JMenuItem("Add");
            add.addActionListener(new commandListener());
            commandMenu.add(add);
            JMenuItem search = new JMenuItem("Search");
            search.addActionListener(new commandListener());
            commandMenu.add(search);
            JMenuItem quit = new JMenuItem("Quit");
            quit.addActionListener(new commandListener());
            commandMenu.add(quit);
            
            bar = new JMenuBar();
            bar.add(commandMenu);
            setJMenuBar(bar);
            
            String message = "<html>Welcome to my Day Planner.<br>Choose a command from the \"Commands\" menu above \nfor adding an activity, searching activities, or quitting the program.<html>";
            
            JLabel startMsg = new JLabel(message, JLabel.CENTER);
            startMsg.setAlignmentX(0);
            startMsg.setAlignmentY(0);
            startPanel.setLayout(new BorderLayout());
            startPanel.add(startMsg);
            startPanel.setVisible(true);
            add(startPanel);
            setVisible(true);
            /*******************************************************************/
            
            /*Add Panel*/
            addPanel.setLayout(new GridLayout(2,0));
            topAdd.setLayout(new GridLayout(0,2));
            infoArea.setLayout(new GridLayout(7,2));
            buttonPanel.setLayout(new GridLayout(2,0));
            JLabel addActivity = new JLabel("<html>Adding an activity:<br><html>");
            JLabel blah = new JLabel("");
            infoArea.add(addActivity);
            infoArea.add(blah);
            JLabel typeLabel = new JLabel("Type: ");
            typeBox.addActionListener(new typeListener());
            typeBox.addItem("School");
            typeBox.addItem("Other");
            typeBox.addItem("Home");
            infoArea.add(typeLabel);
            infoArea.add(typeBox);
            JLabel titleLabel = new JLabel("<html><br>Title: <html>");
            infoArea.add(titleLabel);
            infoArea.add(titleField);
            JLabel startingTimeLabel = new JLabel("<html><br>Starting time (year,month,day,hour,minute):<br><html>");
            infoArea.add(startingTimeLabel);
            infoArea.add(startField);
            JLabel endingTimeLabel = new JLabel("<html><br>Ending time (year,month,day,hour,minute):<br><html>");
            infoArea.add(endingTimeLabel);
            infoArea.add(endField);
            infoArea.add(locationLabel);
            infoArea.add(locationField);
            JLabel commentLabel = new JLabel("<html><br>Comment: <html>");
            infoArea.add(commentLabel);
            infoArea.add(commentField);
            topAdd.add(infoArea);
            addPanel.add(topAdd);
            JButton enter = new JButton("Enter");
            JButton reset = new JButton("Reset");
            enter.addActionListener(new addButtonListener());
            reset.addActionListener(new addButtonListener());
            buttonPanel.add(reset);
            buttonPanel.add(enter);
            topAdd.add(buttonPanel);
            JScrollPane scrollAdd = new JScrollPane(messageAdd);
            scrollAdd.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            scrollAdd.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            addPanel.add(messageAdd);
            buttonPanel.setVisible(true);
            infoArea.setVisible(true);
            topAdd.setVisible(true);
            addPanel.setVisible(false);
            add(addPanel);
            /*******************************************************************/
            
            /*Search Panel*/
            searchPanel.setLayout(new GridLayout(2,0));
            topSearch.setLayout(new GridLayout(0,2));
            searchArea.setLayout(new GridLayout(7,2));
            buttonSearch.setLayout(new GridLayout(2,0));
            JLabel searchActivity = new JLabel("<html>Searching an activity:<br><html>");
            JLabel blahblah = new JLabel("");
            searchArea.add(searchActivity);
            searchArea.add(blahblah);
            JLabel typeSearchLabel = new JLabel("Type: ");
            typeBoxSrch.addItem("School");
            typeBoxSrch.addItem("Other");
            typeBoxSrch.addItem("Home");
            typeBoxSrch.addItem(" ");
            searchArea.add(typeSearchLabel);
            searchArea.add(typeBoxSrch);
            JLabel titleSearchLabel = new JLabel("<html><br>Title: <html>");
            searchArea.add(titleSearchLabel);
            searchArea.add(titleFieldSearch);
            JLabel startingTimeLabelSearch = new JLabel("<html><br>Starting time (year,month,day,hour,minute):<br><html>");
            searchArea.add(startingTimeLabelSearch);
            searchArea.add(startFieldSearch);
            JLabel endingTimeLabelSearch = new JLabel("<html><br>Ending time (year,month,day,hour,minute):<br><html>");
            searchArea.add(endingTimeLabelSearch);
            searchArea.add(endFieldSearch);
            topSearch.add(searchArea);
            searchPanel.add(topSearch);
            JButton enterSearch = new JButton("Enter");
            JButton resetSearch = new JButton("Reset");
            enterSearch.addActionListener(new searchButtonListener());
            resetSearch.addActionListener(new searchButtonListener());
            buttonSearch.add(resetSearch);
            buttonSearch.add(enterSearch);
            topSearch.add(buttonSearch);
            JScrollPane scrollSearch = new JScrollPane(messageSearch);
            scrollSearch.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            scrollSearch.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            searchPanel.add(messageSearch);
            buttonSearch.setVisible(true);
            searchArea.setVisible(true);
            topSearch.setVisible(true);
            searchPanel.setVisible(false);
            add(searchPanel);
            /*******************************************************************/
	}

	/*
	 * Create a time object for the valid input
	 */
	private Time getTime(String line) {
               	String[] tokens = line.split("[ ,\n]+");
               	if (tokens.length != 5)
			return null;
		for (int i = 0; i < 5; i++ )
		    if (!tokens[i].matches("[-+]?[0-9]+"))
			return null;
                int year = Integer.parseInt(tokens[0]);
                int month = Integer.parseInt(tokens[1]);
                int day = Integer.parseInt(tokens[2]);
                int hour = Integer.parseInt(tokens[3]);
                int minute = Integer.parseInt(tokens[4]);
		if (Time.timeOK(year, month, day, hour, minute))
			return new Time(year, month, day, hour, minute);
		else
			return null;
	}
	
	/*
	 * Add a valid home activity
	 */
	private void addHomeActivity(HomeActivity home) {
		if (homesSize == MAX_SIZE)
			messageAdd.setText(messageAdd.getText()+"\nHome list is full: activity not added");
                else{	
                    homes[homesSize++] = home;
                    messageAdd.setText(messageAdd.getText()+"\n"+"Acitivity added to home activities");
            }
	}
	
	/*
	 * Add a valid school activity
	 */
	private void addSchoolActivity(SchoolActivity school) {
		if (schoolsSize == MAX_SIZE)
			messageAdd.setText(messageAdd.getText()+"\nSchool list is full: activity not added");
                else{
                    schools[schoolsSize++] = school;
                    messageAdd.setText(messageAdd.getText()+"\n"+"Acitivity added to School activities");
            }
	}
	
	/*
	 * Add a valid other activity
	 */
	private void addOtherActivity(OtherActivity other) {
		if (othersSize == MAX_SIZE)
			messageAdd.setText(messageAdd.getText()+"\nOther list is full: activity not added");
                else{
			others[othersSize++] = other;
                        messageAdd.setText(messageAdd.getText()+"\n"+"Acitivity added to Other activities");
                }
	}
	
	/** 
	 * Create an activity from the input and add it to the appropriate list
     * @param input
	 */
	public void addActivity(Scanner input) {
		String type = "";
		do {
			System.out.print( "Enter an activity type (home, school, or other)> " );
			type = input.nextLine();
		} while (!matchedKeyword(type, types));
		
		System.out.print("Enter a title> ");
		String title = input.nextLine();
		
		Time startingTime = null;
               	do {
			System.out.print( "Enter a starting time (year, month, day, hour, minute)> " );
			startingTime = getTime( input.nextLine() );
		} while (startingTime == null);

		Time endingTime = null;
               	do {
			System.out.print( "Enter an ending time (year, month, day, hour, minute)> " );
			endingTime = getTime( input.nextLine() );
		} while (endingTime == null);

		String location = "";
		if( type.equalsIgnoreCase("other") || type.equalsIgnoreCase("o") ) {
			System.out.print( "Enter a location> " );
			location = input.nextLine();
		}
		
		System.out.print( "Enter a line of comment> " );
		String comment = input.nextLine();
		
		if (type.equalsIgnoreCase("home") || type.equalsIgnoreCase("h"))
			addHomeActivity(new HomeActivity(title, startingTime, endingTime, comment));
		else if (type.equalsIgnoreCase("school") || type.equalsIgnoreCase("s"))
			addSchoolActivity(new SchoolActivity(title, startingTime, endingTime, comment));
		else
			addOtherActivity(new OtherActivity(title, startingTime, endingTime, location, comment));
	}

	/* 
	 * Check if a keyword is on a list of tokens
	 */
	private boolean matchedKeyword(String keyword, String[] tokens) {
            for (String token : tokens) {
                if (keyword.equalsIgnoreCase(token)) {
                    return true;
                }
            }
		return false;
	}

	/*
	 * Check if all keywords are in a string 
	 */
	private boolean matchedKeywords(String[] keywords, String title) {
		String[] tokens = title.split( "[ ,\n]+" );
            for (String keyword : keywords) {
                if (!matchedKeyword(keyword, tokens)) {
                    return false;
                }
            }
		return true;
	}

	/*
	 * Search for all home activities that satisfy a search request
	 */
	private void searchHomeActivities(Time startingTime, Time endingTime, String[] keywords) {
		for (int i = 0; i < homesSize; i++) 
			if ((startingTime == null || homes[i].getStartingTime().compareTo(startingTime) >= 0) &&
			    (endingTime == null || homes[i].getEndingTime().compareTo(endingTime) <= 0) &&
			    (keywords == null || matchedKeywords(keywords, homes[i].getTitle())))
				System.out.println(homes[i]); 		
	}

	/*
	 * Search for all school activities that satisfy a search request
	 */
	private void searchSchoolActivities(Time startingTime, Time endingTime, String[] keywords) {
		for (int i = 0; i < schoolsSize; i++) 
			if ((startingTime == null || schools[i].getStartingTime().compareTo(startingTime) >= 0) &&
			    (endingTime == null || schools[i].getEndingTime().compareTo(endingTime) <= 0) &&
			    (keywords == null || matchedKeywords(keywords, schools[i].getTitle())))
				System.out.println(schools[i]); 		
	}

	/*
	 * Search for all other activities that satisfy a search request
	 */
	private void searchOtherActivities(Time startingTime, Time endingTime, String[] keywords) {
		for (int i = 0; i < othersSize; i++) 
			if ((startingTime == null || others[i].getStartingTime().compareTo(startingTime) >= 0) &&
			    (endingTime == null || others[i].getEndingTime().compareTo(endingTime) <= 0) &&
			    (keywords == null || matchedKeywords(keywords, others[i].getTitle())))
				System.out.println(others[i]); 		
	}

	/**
	 * Gather a search request and find the matched activites in the related list(s)
     * @param input
	 */ 
	public void searchActivities(Scanner input) {
		String type = "";
		do {
			System.out.print("Enter an activity type (home, school, or other)> ");
			type = input.nextLine();
		} while (!type.isEmpty() && !matchedKeyword(type, types));
		
		Time startingTime = null;
		do {
			System.out.print("Enter a starting time (year, month, day, hour, minute)> ");
			String line = input.nextLine();
			if (line.isEmpty()) 
				break;
			else
				startingTime = getTime(line);
		} while (startingTime == null);

		Time endingTime = null;
		do {
			System.out.print("Enter an ending time (year, month, day, hour, minute)> ");
			String line = input.nextLine();
			if (line.isEmpty()) 
				break;
			 else
				endingTime = getTime(line);
		} while (endingTime == null);

 		System.out.print("Enter title keywords: ");
		String[] keywords = null;
		String line = input.nextLine();
		if (!line.isEmpty())
			keywords = line.split("[ ,\n]+");

		// search for matched activities
		System.out.println("Matched activities: ");
		if (type.isEmpty() || type.equalsIgnoreCase("home") || type.equalsIgnoreCase("h"))
			searchHomeActivities(startingTime, endingTime, keywords);

		if (type.isEmpty() || type.equalsIgnoreCase("school") || type.equalsIgnoreCase("s"))
			searchSchoolActivities(startingTime, endingTime, keywords);

		if (type.isEmpty() || type.equalsIgnoreCase("other") || type.equalsIgnoreCase("o"))
			searchOtherActivities(startingTime, endingTime, keywords);
	}
	
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		DayPlanner planner = new DayPlanner();
	}
}
