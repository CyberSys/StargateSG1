package ui;

import java.awt.*;
import java.text.DecimalFormat;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import faction.Faction;
import faction.Reputation;

import settings.Globals;
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
		writeFactionIntel(doc);
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
			DecimalFormat df = new DecimalFormat( "0.00" );	
			
			doc.insertString(doc.getLength(), "Faction: ", bold);
			doc.insertString(doc.getLength(), mFaction.factionName + NL, null);
			
			doc.insertString(doc.getLength(), "Home World: ", bold);
			doc.insertString(doc.getLength(), getWorldString(mFaction.getHomeWorld()) + NL + NL, null);
			
			doc.insertString(doc.getLength(), "Resources: ", bold);
			doc.insertString(doc.getLength(), mFaction.getNumResources() + NL + NL, null);
			
			doc.insertString(doc.getLength(), "Morale: ", bold);
			doc.insertString(doc.getLength(), df.format(mFaction.morale) + NL + NL, null);
			
			doc.insertString(doc.getLength(), "Military Stats:" + NL, bold);
			doc.insertString(doc.getLength(), TAB + "Troop Count: ", bold);
			doc.insertString(doc.getLength(), mFaction.getNumArmies() + NL, null);
			doc.insertString(doc.getLength(), TAB + "Ship Count: ", bold);
			doc.insertString(doc.getLength(), mFaction.getNumShips() + NL + NL, null);
					
			doc.insertString(doc.getLength(), "Technology Stats:" + NL, bold);
			doc.insertString(doc.getLength(), TAB + "Resource Efficiency: ", bold);
			doc.insertString(doc.getLength(), df.format(mFaction.getTechLevel().resourceEfficiency) + (mFaction.getTechLevel().isMaximum(Globals.RESOURCE_RESEARCH) ? " [MAX]" : "") + NL, null);
			doc.insertString(doc.getLength(), TAB + "Offensive Tech Level: ", bold);
			doc.insertString(doc.getLength(), df.format(mFaction.getTechLevel().offensiveCapabilities) + (mFaction.getTechLevel().isMaximum(Globals.OFFENSE_RESEARCH) ? " [MAX]" : "") + NL, null);
			doc.insertString(doc.getLength(), TAB + "Defensive Tech Level: ", bold);
			doc.insertString(doc.getLength(), df.format(mFaction.getTechLevel().defensiveCapabilities) + (mFaction.getTechLevel().isMaximum(Globals.DEFENSE_RESEARCH) ? " [MAX]" : "") + NL + NL, null);
		} 
		catch (BadLocationException e){}
	}
	
	private void writeFactionIntel(Document doc)
	{
		boolean hasIntel = false;
		
		try
		{
			doc.insertString(doc.getLength(), "Faction Intelligence: " + NL, bold);
			
			for(Faction f : Universe.factions)
			{
				if(mFaction.hasIntel(f) && !f.equals(mFaction))
				{
					hasIntel = true;
					
					DecimalFormat df = new DecimalFormat( "0.00" );
					doc.insertString(doc.getLength(), TAB + f.factionName + ":" + NL, bold);
					doc.insertString(doc.getLength(), TAB + TAB + "Morale: ", bold);
					doc.insertString(doc.getLength(), df.format(f.morale) + NL, null);
					doc.insertString(doc.getLength(), TAB + TAB + "Resource Efficiency: ", bold);
					doc.insertString(doc.getLength(), df.format(f.getTechLevel().resourceEfficiency) + (mFaction.getTechLevel().isMaximum(Globals.RESOURCE_RESEARCH) ? " [MAX]" : "") + NL, null);
					doc.insertString(doc.getLength(), TAB + TAB + "Offensive Tech Level: ", bold);
					doc.insertString(doc.getLength(), df.format(f.getTechLevel().offensiveCapabilities) + (mFaction.getTechLevel().isMaximum(Globals.OFFENSE_RESEARCH) ? " [MAX]" : "") + NL, null);
					doc.insertString(doc.getLength(), TAB + TAB + "Defensive Tech Level: ", bold);
					doc.insertString(doc.getLength(), df.format(f.getTechLevel().defensiveCapabilities) + (mFaction.getTechLevel().isMaximum(Globals.DEFENSE_RESEARCH) ? " [MAX]" : "") + NL, null);
				}
			}
			
			if(!hasIntel)
				doc.insertString(doc.getLength(), TAB + "No Intel Available" + NL, null);
			
			doc.insertString(doc.getLength(), NL, null);
		}
		catch (BadLocationException e){}
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
			
			doc.insertString(doc.getLength(), NL, null);
		} 
		catch (BadLocationException e){}
	}
	
	private void writeWorldStats(Document doc)
	{
		try 
		{
			doc.insertString(doc.getLength(), "Owned World Statistics: " + NL, bold);
			
			for(World w : mFaction.getControlledWorlds())
			{				
				writeWorldStats(doc, w);
			}
			
			doc.insertString(doc.getLength(), "Other World Statistics: " + NL, bold);
			
			if(mFaction.getKnownWorlds().size() - mFaction.getControlledWorlds().size() > 0)
			{
				for(World w : mFaction.getKnownWorlds())
				{		
					if(w.getControllingFaction().equals(mFaction))
						continue;
					
					writeWorldStats(doc, w);
				}
			}
		} 
		catch (BadLocationException e){}
	}
	
	private void writeWorldStats(Document doc, World w) throws BadLocationException
	{
		doc.insertString(doc.getLength(), TAB + getWorldString(w) + ": " + NL, bold);
		
		if(w.hasIntel(mFaction))
		{
			for(Faction f : w.getOccupyingFactions())
			{
				doc.insertString(doc.getLength(), TAB + TAB + f.factionName + ": " + NL, bold);
				
				doc.insertString(doc.getLength(), TAB + TAB + TAB + "Troops: ", bold);
				doc.insertString(doc.getLength(), f.getNumArmies(w) + NL, null);
				doc.insertString(doc.getLength(), TAB + TAB + TAB + "Ship: ", bold);
				doc.insertString(doc.getLength(), f.getNumShips(w) + NL, null);
			}
			
			doc.insertString(doc.getLength(), NL, null);
		}
		else
		{
			doc.insertString(doc.getLength(), TAB + TAB + "No Intel Available" + NL + NL, null);
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
