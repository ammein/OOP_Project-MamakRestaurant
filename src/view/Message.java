package view;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.plaf.DimensionUIResource;

public class Message extends JPanel {
	
	public Message(String title, String bodyMessage) {
		setLayout(new BorderLayout(20, 20));
		JLabel title_message = new JLabel(title);
		title_message.setHorizontalAlignment(SwingConstants.CENTER);
		title_message.setFont(new Font("Lucida Grande", Font.PLAIN, 24));
		add(title_message, BorderLayout.NORTH);
		
		JButton button = new JButton("Okay");
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JComponent comp = (JComponent) e.getSource();
				Window win = SwingUtilities.getWindowAncestor(comp);
				win.dispose();
			}
			
		});
		
		JPanel message_container = new JPanel();
		add(message_container, BorderLayout.CENTER);
		message_container.setLayout(new BorderLayout(0, 0));
		
		JLabel message = new JLabel("<html><div style=\"text-align:center; height:auto; \">" + bodyMessage + "</div></html>");
		message.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		message_container.add(message, BorderLayout.NORTH);
		button.setFont(UIManager.getFont("Button.font"));
		add(button, BorderLayout.SOUTH);
	}

	public void design() {
		EventQueue.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				JFrame frame = new JFrame();
				Message UserMessage = new Message("Title Message", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. In posuere leo lacinia, pulvinar augue et, bibendum ante. Cras sollicitudin placerat mauris ac commodo. Pellentesque id viverra lectus. Donec et porta risus. Nam metus dolor, egestas non porta a, facilisis ac felis. Cras a neque vel ante iaculis posuere.");
				frame.getContentPane().add(UserMessage);
				frame.pack();
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
			}
		});
	}
}
