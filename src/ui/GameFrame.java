package ui;

import java.awt.Dimension;

import javax.swing.*;

public class GameFrame extends JFrame 
{
	//
	// DATA
	//
	private static final long serialVersionUID = -8359900383505634479L;
	
    private JButton mAcceptButton;
    
    private JPanel mStatPanel;
    private JPanel mAdvisorPanel;
    
    private JTextField mPlayerInput;
    
    private JScrollPane mLogScroll;
    private JTextPane mLog;
    
    private JScrollPane mPromptScroll;
    private JTextPane mPrompt;
	
	//
	// CTOR
	//
	public GameFrame()
	{
		init();
	}
	
	private void init()
	{
        mStatPanel = new JPanel();
        mAdvisorPanel = new JPanel();
        mPlayerInput = new JTextField();
        mAcceptButton = new JButton();
        mPromptScroll = new JScrollPane();
        mPrompt = new JTextPane();
        mLogScroll = new JScrollPane();
        mLog = new JTextPane();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        mStatPanel.setBorder(BorderFactory.createEtchedBorder());

        GroupLayout jPanel1Layout = new GroupLayout(mStatPanel);
        mStatPanel.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 147, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 344, Short.MAX_VALUE)
        );

        mAdvisorPanel.setBorder(BorderFactory.createEtchedBorder());
        
        GroupLayout jPanel2Layout = new GroupLayout(mAdvisorPanel);
        mAdvisorPanel.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 147, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 140, Short.MAX_VALUE)
        );

        mPlayerInput.setText("");

        mAcceptButton.setText("Accept");

        mPromptScroll.setViewportView(mPrompt);

        mLogScroll.setViewportView(mLog);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                    .addComponent(mAdvisorPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(mStatPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                    .addComponent(mLogScroll, GroupLayout.DEFAULT_SIZE, 757, Short.MAX_VALUE)
                    .addComponent(mPromptScroll, GroupLayout.DEFAULT_SIZE, 757, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(mPlayerInput, GroupLayout.DEFAULT_SIZE, 678, Short.MAX_VALUE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(mAcceptButton)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(mStatPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(mLogScroll, GroupLayout.DEFAULT_SIZE, 348, Short.MAX_VALUE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(mPromptScroll, GroupLayout.PREFERRED_SIZE, 115, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(mPlayerInput, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addComponent(mAcceptButton)))
                    .addComponent(mAdvisorPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        mAdvisorPanel.setMinimumSize(new Dimension(147, 140));
        
        pack();
	}
	
	public static void main(String args[]) {

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new GameFrame().setVisible(true);
            }
        });
        
    }
}
