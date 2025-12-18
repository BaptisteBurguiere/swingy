package swingy.Model;

import java.time.LocalDateTime;

public class GameStats
{
	public int damage_dealt = 0;
	public int damage_received = 0;
	public int crit = 0;
	public int missed = 0;
	public int dodged = 0;
	public int parried = 0;
	public int flee_failed = 0;
	public int flee_success = 0;
	public int villain_slained = 0;
	public int rooms_exited = 0;
	public int boss_slained = 0;
	public int items_equiped = 0;
	public int chest_opened = 0;
	public boolean has_won = false;
	public LocalDateTime win_date = LocalDateTime.now();
	public boolean endless_mode = false;
}
