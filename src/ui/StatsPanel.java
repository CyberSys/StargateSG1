package ui;

import java.awt.*;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import faction.Faction;
import faction.Reputation;

import universe.*;

public class StatsPanel extends JPanel 
{
	//
	// DATA
	//
	private static final long serialVersionUID = 6814259108834609516L;
	
	private JLabel mTitleLabel;
    private JScrollPane mStatsScroll;
    private JTextPane mStats;
	
    private static final String NL = System.getProperty("line.separator");
    private static final String TAB = "     ";
	
    private static final SimpleAttributeSet bold = new SimpleAttributeSet();
    
    // Managed Faction
    private Faction mFaction;
    
	public StatsPanel(Faction f)
	{		
		init();
		
		bold.addAttribute(StyleConstants.CharacterConstants.Bold, Boolean.TRUE);
		
		mFaction = f;
		updateDisplay();
	}
	
	public void updateDisplay()
	{		
		DefaultStyledDocument doc = new DefaultStyledDocument();
		
		writeFactionStats(doc);
		writeFactionReps(doc);
		writeWorldStats(doc);
		
		mStats.setDocument(doc);
	}
	
	private void init()
	{
		mTitleLabel = new JLabel();
        mStatsScroll = new JScrollPane();
        mStats = new JTextPane();

        setPreferredSize(new Dimension(219, 394));

        mTitleLabel.setFont(new Font("Tahoma", 1, 14)); // NOI18N
        mTitleLabel.setText("Current Player Statistics");

        mStatsScroll.setBorder(null);

        mStats.setBorder(null);
        mStats.setEditable(false);
        mStats.setFocusable(false);
        mStats.setOpaque(false);
        //mStats.setEnabled(false);
        //mStats.setDisabledTextColor(Color.BLACK);
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
		try 
		{
			doc.insertString(doc.getLength(), "Faction: ", bold);
			doc.insertString(doc.getLength(), mFaction.factionName + NL, null);
			
			doc.insertString(doc.getLength(), "Home World: ", bold);
			doc.insertString(doc.getLength(), getWorldString(mFaction.getHomeWorld()) + NL + NL, null);
			
			doc.insertString(doc.getLength(), "Resources: ", bold);
			doc.insertString(doc.getLength(), mFaction.getNumResources() + NL + NL, null);
			
			doc.insertString(doc.getLength(), "Military Stats:" + NL, bold);
			doc.insertString(doc.getLength(), TAB + "Troop Count: ", bold);
			doc.insertString(doc.getLength(), mFaction.getNumArmies() + NL, null);
			doc.insertString(doc.getLength(), TAB + "Ship Count: ", bold);
			doc.insertString(doc.getLength(), mFaction.getNumShips() + NL + NL, null);
		} 
		catch (BadLocationException e) 
		{
			e.printStackTrace();
		}
	}
	
	private void writeFactionReps(Document doc)
	{
		try 
		{
			doc.insertString(doc.getLength(), "Faction Reputations: " + NL, bold);
			
			for(Faction f : Universe.factions)
			{
				if(f.equals(mFaction))
					continue;
				
				Reputation.ReputationLevel rLevel = f.getReputation(mFaction);
				
				double ratio;
				Color c1;
				Color c2;
				
				if(rLevel.threshold < 50)
				{
					c1 = Color.RED;
					c2 = Color.BLACK;
					ratio = rLevel.threshold / 50.0;
				}
				else
				{
					c1 = Color.BLACK;
					c2 = Color.GREEN;
					ratio = (rLevel.threshold - 50.0) / 50.0;
				}
				
				int r = (int)(c1.getRed() * (1 - ratio) + c2.getRed() * (ratio));
				int g = (int)(c1.getGreen() * (1 - ratio) + c2.getGreen() * (ratio));
				int b = (int)(c1.getBlue() * (1 - ratio) + c2.getBlue() * (ratio));
				
				Color res = new Color(r, g, b);
				
				SimpleAttributeSet color = new SimpleAttributeSet();
				color.addAttribute(StyleConstants.ColorConstants.Foreground, res);
				
				doc.insertString(doc.getLength(), TAB + f.factionName + ": ", bold);
				doc.insertString(doc.getLength(), rLevel.toString() + NL, color);
			}
		} 
		catch (BadLocationException e) 
		{
			e.printStackTrace();
		}
	}
	
	private void writeWorldStats(Document doc)
	{
		try 
		{
			doc.insertString(doc.getLength(), NL + "Owned World Statistics: " + NL, bold);
			
			for(World w : mFaction.getControlledWorlds())
			{				
				doc.insertString(doc.getLength(), TAB + getWorldString(w) + ": " + NL, bold);
				
				doc.insertString(doc.getLength(), TAB + TAB + "Troops: ", bold);
				doc.insertString(doc.getLength(), mFaction.getNumArmies(w) + NL, null);
				doc.insertString(doc.getLength(), TAB + TAB + "Ships: ", bold);
				doc.insertString(doc.getLength(), mFaction.getNumShips(w) + NL + NL, null);
			}
			
			doc.insertString(doc.getLength(), "Other World Statistics: " + NL, bold);
			
			for(World w : mFaction.getKnownWorlds())
			{		
				if(w.getControllingFaction().equals(mFaction))
					continue;
				
				doc.insertString(doc.getLength(), TAB + getWorldString(w) + ": " + NL, bold);
				
				doc.insertString(doc.getLength(), TAB + TAB + "Troops: ", bold);
				doc.insertString(doc.getLength(), mFaction.getNumArmies(w) + NL, null);
				doc.insertString(doc.getLength(), TAB + TAB + "Ship: ", bold);
				doc.insertString(doc.getLength(), mFaction.getNumShips(w) + NL + NL, null);
			}
		} 
		catch (BadLocationException e) 
		{
			e.printStackTrace();
		}
	}
	
	private String getWorldString(World w)
	{
		String wStr = w.name;
		
		if(mFaction.knowsGateAddress(w))
		{
			wStr += " (" + w.address + ")";
		}
		
		if(!w.getControllingFaction().equals(mFaction))
		{
			wStr += " - " + w.getControllingFaction().factionName;
		}
		
		return wStr;
	}
}
