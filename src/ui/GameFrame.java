package ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import ui.prompt.PromptTree;
import universe.Universe;

public class GameFrame extends JFrame 
{
	//
	// DATA
	//
	private static final long serialVersionUID = -8359900383505634479L;
	
	// Instance
	private static GameFrame mSingleton;
	
	// GUI Stuffs
	private Box.Filler filler1;
    private Box.Filler filler2;
    private JButton mAcceptButton;
    private JPanel mAdvisorPanel;
    private JButton mDiplomaticAdviceButton;
    private JTextPane mLog;
    private JScrollPane mLogScroll;
    private JButton mMilitaryAdviceButton;
    private JTextField mPlayerInput;
    private JTextPane mPrompt;
    private JScrollPane mPromptScroll;
    private JButton mScientificAdviceButton;
    private StatsPanel mStatsPanel;
    
    // GUI Data
    private List<TitledLine> mCurrentLog;
    private Stack<List<TitledLine>> mLogHistory;
    
    private PromptTree mCurrentPrompt;
    
	//
	// CTOR
	//
	private GameFrame()
	{
		init();
		
		mCurrentLog = new ArrayList<TitledLine>();
		mLogHistory = new Stack<List<TitledLine>>();
		
		mCurrentPrompt = Universe.playerFaction.getAvailableActions();
		mCurrentPrompt.writePrompt(mPrompt);
		
		addToLogI("Game Start");
	}
	
	public static GameFrame getGameFrame()
	{
		if(mSingleton == null)
		{
			try {
	            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
	                if ("Nimbus".equals(info.getName())) {
	                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
	                    break;
	                }
	            }
	        } catch (ClassNotFoundException ex) {
	            java.util.logging.Logger.getLogger(GameFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
	        } catch (InstantiationException ex) {
	            java.util.logging.Logger.getLogger(GameFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
	        } catch (IllegalAccessException ex) {
	            java.util.logging.Logger.getLogger(GameFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
	        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
	            java.util.logging.Logger.getLogger(GameFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
	        }
			
			mSingleton = new GameFrame();
		}
		
		return mSingleton;
	}
	
	public static void addToLog(String body)
	{
		mSingleton.addToLogI(body);
	}
	
	public static void addToLog(String title, String body)
	{
		mSingleton.addToLogI(title, body);
	}
	
	public void addToLogI(String body)
	{
		addToLogI(null, body);
	}
	
	public void addToLogI(String title, String body)
	{
		final String lineSep = System.getProperty("line.separator");
		
		mCurrentLog.add(new TitledLine(title, body + lineSep));
		
		updateTextPane(mLog, mCurrentLog);
		
		mLog.scrollRectToVisible(new Rectangle(0,mLog.getHeight() + 50,1,1));
	}
	
	public void enableInput()
	{
		mPlayerInput.setText("");
		mPlayerInput.setEnabled(true);
		mAcceptButton.setEnabled(true);
		
		mPrompt.setEnabled(true);
		
		mStatsPanel.updateDisplay();
	}
	
	private void updateTextPane(JTextPane pane, List<TitledLine> data)
	{
		StyledDocument doc = new DefaultStyledDocument();
		
		for(TitledLine line : data)
		{
			writeDocLine(doc, line);
		}
		
		pane.setDocument(doc);
	}
	
	
	private void writeDocLine(StyledDocument doc, TitledLine line)
	{
		final String lineSep = System.getProperty("line.separator");
		
		final SimpleAttributeSet titleStyle = new SimpleAttributeSet();
		titleStyle.addAttribute(StyleConstants.CharacterConstants.Bold, Boolean.TRUE);
		
		final SimpleAttributeSet bodyStyle = new SimpleAttributeSet();
		bodyStyle.addAttribute(StyleConstants.CharacterConstants.Italic, Boolean.TRUE);
		
		try 
		{
			if(line.title != null)
				doc.insertString(doc.getLength(), line.title + ": ", titleStyle);
			
			doc.insertString(doc.getLength(), line.body + lineSep, bodyStyle);
		} 
		catch (BadLocationException e){}
	}
	
	
	private void init()
	{
		mStatsPanel = new StatsPanel(Universe.playerFaction);
        mAdvisorPanel = new JPanel();
        mMilitaryAdviceButton = new JButton();
        mScientificAdviceButton = new JButton();
        mDiplomaticAdviceButton = new JButton();
        filler1 = new Box.Filler(new Dimension(0, 0), new Dimension(0, 0), new Dimension(0, 32767));
        filler2 = new Box.Filler(new Dimension(0, 0), new Dimension(0, 0), new Dimension(0, 32767));
        mPlayerInput = new JTextField();
        mAcceptButton = new JButton();
        mPromptScroll = new JScrollPane();
        mPrompt = new JTextPane();
        mLogScroll = new JScrollPane();
        mLog = new JTextPane();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(800, 600));

        mStatsPanel.setBorder(BorderFactory.createEtchedBorder());

        mAdvisorPanel.setBorder(BorderFactory.createEtchedBorder());

        mMilitaryAdviceButton.setText("Ask Military Advisor");
        mMilitaryAdviceButton.addActionListener(new ActionListener()
        {
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				Universe.getMilitaryAdvice();
				mPlayerInput.requestFocusInWindow();
			}
        });

        mScientificAdviceButton.setText("Ask Scientific Advisor");
        mScientificAdviceButton.addActionListener(new ActionListener()
        {
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				Universe.getScienceAdvice();
				mPlayerInput.requestFocusInWindow();
			}
        });

        mDiplomaticAdviceButton.setText("Ask Diplomatic Advisor");
        mDiplomaticAdviceButton.addActionListener(new ActionListener()
        {
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				Universe.getDiplomacyAdvice();
				mPlayerInput.requestFocusInWindow();
			}
        });

        GroupLayout mAdvisorPanelLayout = new GroupLayout(mAdvisorPanel);
        mAdvisorPanel.setLayout(mAdvisorPanelLayout);
        mAdvisorPanelLayout.setHorizontalGroup(
            mAdvisorPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(mAdvisorPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mAdvisorPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(filler1, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE)
                    .addComponent(mMilitaryAdviceButton, GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE)
                    .addComponent(mScientificAdviceButton, GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE)
                    .addComponent(filler2, GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE)
                    .addComponent(mDiplomaticAdviceButton, GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE))
                .addContainerGap())
        );
        mAdvisorPanelLayout.setVerticalGroup(
            mAdvisorPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(mAdvisorPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(mMilitaryAdviceButton)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(filler1, GroupLayout.PREFERRED_SIZE, 12, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(mScientificAdviceButton)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(filler2, GroupLayout.PREFERRED_SIZE, 12, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(mDiplomaticAdviceButton)
                .addContainerGap())
        );

        mPlayerInput.addKeyListener(new KeyAdapter()
        {
			@Override
			public void keyPressed(KeyEvent arg0) 
			{
				if(arg0.getKeyCode() == KeyEvent.VK_ENTER)
				{
					doAction();
				}
			}
        });
        
        mAcceptButton.setText("Accept");
        mAcceptButton.addActionListener(new ActionListener()
        {
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				doAction();
			}
        });

        mPrompt.setEditable(false);
        mPrompt.setFocusable(false);
        mPromptScroll.setViewportView(mPrompt);

        mLog.setEditable(false);
        mLog.setFocusable(false);
        mLogScroll.setViewportView(mLog);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                    .addComponent(mAdvisorPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(mStatsPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                    .addComponent(mPromptScroll, GroupLayout.DEFAULT_SIZE, 673, Short.MAX_VALUE)
                    .addComponent(mLogScroll, GroupLayout.DEFAULT_SIZE, 673, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(mPlayerInput, GroupLayout.DEFAULT_SIZE, 602, Short.MAX_VALUE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(mAcceptButton)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(mLogScroll, GroupLayout.DEFAULT_SIZE, 394, Short.MAX_VALUE)
                    .addComponent(mStatsPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(mPromptScroll)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                            .addComponent(mPlayerInput, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addComponent(mAcceptButton)))
                    .addComponent(mAdvisorPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
	}
	
	public void setVisible(boolean v)
	{
		super.setVisible(v);
		
		mStatsPanel.updateDisplay();
		
		mCurrentPrompt = Universe.playerFaction.getAvailableActions();
		mCurrentPrompt.writePrompt(mPrompt);
		
		mPlayerInput.requestFocusInWindow();
	}
	
	private void doAction()
	{
		String actionString = mPlayerInput.getText();		
		
		mPlayerInput.setEnabled(false);
		mAcceptButton.setEnabled(false);
		mPrompt.setEnabled(false);
		
		PromptTree next = mCurrentPrompt.getNextPrompt(actionString);
		
		if(next.isLeaf())
		{
			//switchLog();
			
			Universe.playerFaction.setNextAction(next.getTask());
			Universe.elapseTime();
			mCurrentPrompt = Universe.playerFaction.getAvailableActions();
		}
		else
		{
			mCurrentPrompt = next;
		}
		
		mCurrentPrompt.writePrompt(mPrompt);
		
		enableInput();
		mPlayerInput.requestFocusInWindow();
	}
	
	private void switchLog()
	{
		mLogHistory.push(mCurrentLog);
		mCurrentLog = new ArrayList<TitledLine>();
		
		updateTextPane(mLog, mCurrentLog);
	}
	
	
	//
	// INNER CLASS
	//
	private class TitledLine
	{
		private String title;
		private String body;
		
		public TitledLine(String t, String b)
		{
			title = t;
			body = b;
		}
	}
}
