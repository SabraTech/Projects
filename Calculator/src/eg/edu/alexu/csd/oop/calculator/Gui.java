package eg.edu.alexu.csd.oop.calculator;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

// TODO: Auto-generated Javadoc
/**
 * The Class Gui.
 */
public class Gui {

	/** The view. */
	private String input = "", view = "";
	
	/** The frame. */
	private JFrame frame;
	
	/** The screen. */
	private JTextField screen;
	
	/** The done. */
	private JButton one, two, three, four, five, six, seven, eight, nine, zero,
			plus, sub, times, divide, equals, dot, btnC, save, load, current,
			next, prev, done;
	
	/** The calc. */
	Engine calc = new Engine();

	/**
	 * Launch the application.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Gui window = new Gui();
					window.frame.setVisible(true);
					window.frame.setResizable(false);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Gui() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		frame = new JFrame();
		frame.setSize(350, 470);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		screen = new JTextField();
		screen.setBounds(10, 11, 314, 66);
		frame.getContentPane().add(screen);
		screen.setColumns(10);

		Numbers N = new Numbers();
		Operations P = new Operations();
		Navigation G = new Navigation();

		one = new JButton("1");
		one.addActionListener(N);
		one.setBounds(10, 223, 54, 39);
		frame.getContentPane().add(one);

		two = new JButton("2");
		two.addActionListener(N);
		two.setBounds(74, 223, 54, 39);
		frame.getContentPane().add(two);

		three = new JButton("3");
		three.addActionListener(N);
		three.setBounds(138, 223, 54, 39);
		frame.getContentPane().add(three);

		divide = new JButton("/");
		divide.addActionListener(P);
		divide.setBounds(202, 223, 54, 39);
		frame.getContentPane().add(divide);

		six = new JButton("6");
		six.addActionListener(N);
		six.setBounds(138, 273, 54, 39);
		frame.getContentPane().add(six);

		five = new JButton("5");
		five.addActionListener(N);
		five.setBounds(74, 273, 54, 39);
		frame.getContentPane().add(five);

		four = new JButton("4");
		four.addActionListener(N);
		four.setBounds(10, 273, 54, 39);
		frame.getContentPane().add(four);

		nine = new JButton("9");
		nine.addActionListener(N);
		nine.setBounds(138, 323, 54, 39);
		frame.getContentPane().add(nine);

		eight = new JButton("8");
		eight.addActionListener(N);
		eight.setBounds(74, 323, 54, 39);
		frame.getContentPane().add(eight);

		seven = new JButton("7");
		seven.addActionListener(N);
		seven.setBounds(10, 323, 54, 39);
		frame.getContentPane().add(seven);

		times = new JButton("*");
		times.addActionListener(P);
		times.setBounds(202, 273, 54, 39);
		frame.getContentPane().add(times);

		sub = new JButton("-");
		sub.addActionListener(P);
		sub.setBounds(202, 323, 54, 39);
		frame.getContentPane().add(sub);

		zero = new JButton("0");
		zero.addActionListener(N);
		zero.setBounds(74, 373, 54, 39);
		frame.getContentPane().add(zero);

		dot = new JButton(".");
		dot.addActionListener(N);
		dot.setBounds(138, 373, 54, 39);
		frame.getContentPane().add(dot);

		equals = new JButton("=");
		equals.addActionListener(P);
		equals.setBounds(262, 323, 62, 89);
		frame.getContentPane().add(equals);

		save = new JButton("Save");
		save.addActionListener(G);
		save.setBounds(175, 165, 149, 39);
		frame.getContentPane().add(save);

		load = new JButton("Load");
		load.addActionListener(G);
		load.setBounds(10, 165, 155, 39);
		frame.getContentPane().add(load);

		current = new JButton("Current");
		current.addActionListener(G);
		current.setBounds(233, 107, 91, 47);
		frame.getContentPane().add(current);

		next = new JButton("Next");
		next.addActionListener(G);
		next.setBounds(10, 107, 74, 47);
		frame.getContentPane().add(next);

		prev = new JButton("Prev");
		prev.addActionListener(G);
		prev.setBounds(128, 107, 74, 47);
		frame.getContentPane().add(prev);

		done = new JButton("Input");
		done.addActionListener(P);
		done.setBounds(262, 223, 62, 89);
		frame.getContentPane().add(done);

		plus = new JButton("+");
		plus.addActionListener(P);
		plus.setBounds(202, 373, 54, 39);
		frame.getContentPane().add(plus);

		btnC = new JButton("C");
		btnC.addActionListener(P);
		btnC.setBounds(10, 373, 54, 39);
		frame.getContentPane().add(btnC);
	}

	/**
	 * The Class Numbers.
	 */
	private class Numbers implements ActionListener {

		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			JButton src = (JButton) e.getSource();

			if (src.equals(one)) {
				input += "1";
				screen.setText(input);
			} else if (src.equals(two)) {
				input += "2";
				screen.setText(input);
			} else if (src.equals(three)) {
				input += "3";
				screen.setText(input);
			} else if (src.equals(four)) {
				input += "4";
				screen.setText(input);
			} else if (src.equals(five)) {
				input += "5";
				screen.setText(input);
			} else if (src.equals(six)) {
				input += "6";
				screen.setText(input);
			} else if (src.equals(seven)) {
				input += "7";
				screen.setText(input);
			} else if (src.equals(eight)) {
				input += "8";
				screen.setText(input);
			} else if (src.equals(nine)) {
				input += "9";
				screen.setText(input);
			} else if (src.equals(zero)) {
				input += "0";
				screen.setText(input);
			} else if (src.equals(dot)) {
				input += ".";
				screen.setText(input);
			}
		}

	}

	/**
	 * The Class Operations.
	 */
	private class Operations implements ActionListener {

		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			JButton src = (JButton) e.getSource();

			if (src.equals(plus)) {
				input += "+";
				screen.setText(input);
			} else if (src.equals(sub)) {
				input += "-";
				screen.setText(input);
			} else if (src.equals(times)) {
				input += "*";
				screen.setText(input);
			} else if (src.equals(divide)) {
				input += "/";
				screen.setText(input);
			} else if (src.equals(btnC)) {
				if (input.length() != 0) {
					input = input.substring(0, input.length() - 1);
				}
				screen.setText(input);
			} else if (src.equals(done)) {
				try {
					calc.input(input);
					screen.setText("Input added to History!");
				} catch (Exception E) {
					screen.setText("ERROR!");
				}
			} else if (src.equals(equals)) {
				String ans = "";
				try {
					ans = calc.getResult();
					screen.setText(ans);
					input = "";
				} catch (Exception E) {
					String error = "Syntax/Math Error!";
					screen.setText(error);
					input = "";
				}
			}
		}

	}

	/**
	 * The Class Navigation.
	 */
	private class Navigation implements ActionListener {

		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			JButton src = (JButton) e.getSource();
			if (src.equals(current)) {
				view = calc.current();
				if (view == null) {
					JOptionPane.showMessageDialog(null, "No current results",
							"Warning", JOptionPane.INFORMATION_MESSAGE);
				} else {
					screen.setText(view);
				}
			} else if (src.equals(next)) {
				view = calc.next();
				if (view == null) {
					JOptionPane.showMessageDialog(null, "No next results",
							"Warning", JOptionPane.INFORMATION_MESSAGE);
				} else {
					screen.setText(view);
				}
			} else if (src.equals(prev)) {
				view = calc.prev();
				if (view == null) {
					JOptionPane.showMessageDialog(null, "No prevous results",
							"Warning", JOptionPane.INFORMATION_MESSAGE);
				} else {
					screen.setText(view);
				}
			} else if (src.equals(save)) {
				try {
					calc.save();
					JOptionPane.showMessageDialog(null, "Saved");
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "Error in saving!");
				}

			} else if (src.equals(load)) {
				try {
					calc.load();
					JOptionPane.showMessageDialog(null, "Loaded");
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null,
							"'Memory.txt' File not found or unable to read!");
				}
			}

		}

	}
}