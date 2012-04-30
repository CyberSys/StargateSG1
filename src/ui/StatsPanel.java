package ui;

import java.awt.*;

import javax.swing.*;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import faction.Faction;

public class StatsPanel extends JPanel 
{
	//
	// DATA
	//
	private static final long serialVersionUID = 6814259108834609516L;
	
	private JLabel mTitleLabel;
    private JScrollPane mStatsScroll;
    private JTextPane mStats;
	
    private final String NL = System.getProperty("line.separator");
    private final String TAB = "\t";
	
    private final SimpleAttributeSet bold = new SimpleAttributeSet();
	
	private final SimpleAttributeSet italic = new SimpleAttributeSet();
    
    // Managed Faction
    private Faction mFaction;
    
	public StatsPanel(Faction f)
	{		
		init();
		
		bold.addAttribute(StyleConstants.CharacterConstants.Bold, Boolean.TRUE);
		italic.addAttribute(StyleConstants.CharacterConstants.Italic, Boolean.TRUE);
		
		mFaction = f;
		updateDisplay();
	}
	
	public void updateDisplay()
	{		
		Document doc = new DefaultStyledDocument();
		
		writeFactionStats(doc);
	}
	
	private void init()
	{
		mTitleLabel = new JLabel();
        mStatsScroll = new JScrollPane();
        mStats = new JTextPane();

        setPreferredSize(new Dimension(219, 394));

        mTitleLabel.setFont(new Font("Tahoma", 1, 14)); // NOI18N
        mTitleLabel.setText("Current Statistics");

        mStatsScroll.setBorder(null);

        mStats.setBorder(null);
        mStats.setEditable(false);
        mStats.setEnabled(false);
        mStats.setFocusable(false);
        mStatsScroll.setViewportView(mStats);

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(mTitleLabel)
                .addContainerGap(109, Short.MAX_VALUE))
            .addComponent(mStatsScroll, GroupLayout.DEFAULT_SIZE, 239, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(mTitleLabel)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(mStatsScroll, GroupLayout.DEFAULT_SIZE, 410, Short.MAX_VALUE))
        );
	}
	
	private void writeFactionStats(Document doc)
	{
		
	}
}
