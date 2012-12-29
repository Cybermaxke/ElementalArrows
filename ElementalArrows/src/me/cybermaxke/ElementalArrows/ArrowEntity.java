package me.cybermaxke.ElementalArrows;

import java.lang.reflect.Field;

import org.bukkit.craftbukkit.v1_4_6.entity.CraftItem;
import org.bukkit.craftbukkit.v1_4_6.inventory.CraftItemStack;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerPickupItemEvent;

import me.cybermaxke.ElementalArrows.Materials.CustomArrowItem;

import net.minecraft.server.v1_4_6.EntityArrow;
import net.minecraft.server.v1_4_6.EntityHuman;
import net.minecraft.server.v1_4_6.EntityItem;
import net.minecraft.server.v1_4_6.EntityLiving;
import net.minecraft.server.v1_4_6.Item;
import net.minecraft.server.v1_4_6.ItemStack;
import net.minecraft.server.v1_4_6.World;

public class ArrowEntity extends EntityArrow {
	
	private CustomArrowItem arrow;
	private float power;	
	private int damage = 0;
	private int knockback = 0;
	private int fireticks = 0;
	private boolean canPickup = true;
	
	public ArrowEntity(World world, double x, double y, double z) {
		super(world, x, y, z);
	}
	
	public ArrowEntity(World world, EntityLiving entityliving, EntityLiving entityliving1, float f, float f1) {
		super(world, entityliving, entityliving1, f, f1);
		this.power = f;
	}
	
	public ArrowEntity(World world, EntityLiving entityliving, float f) {
		super(world, entityliving, f);
		this.power = f;		
	}
	
	public void setCanPickup(boolean pickup) {
		this.canPickup = pickup;
	}
	
	public boolean canPickup() {
		return this.canPickup;
	}
	
	public ArrowEntity(World world) {
		super(world);
	}
	
	public float getPower() {
		return this.power;
	}
	
	public void setArrow(CustomArrowItem arrow) {
		this.arrow = arrow;
	}
	
	public CustomArrowItem getArrow() {
		return this.arrow;
	}
	 
	public void setDamage(int amount) {
		if (amount > 0)
			this.b(this.c() + (double) amount * 0.5D + 0.5D);
		this.damage = amount;
	}
	
	public int getDamage() {
		return this.damage;
	}
	
	public void setKnockback(int amount) {
		if (amount > 0)
			this.a(amount);
		this.knockback = amount;
	}
	
	public int getKnockback() {
		return this.knockback;
	}
	
	public void setFireTicks(int amount) {
		if (amount > 0)
			this.setOnFire(amount);
		this.fireticks = amount;
	}
	
	public int getFireTicks() {
		return this.fireticks;
	}
	
	public boolean inGround() {
		boolean inGround = false;
		
		try {
			Field f = EntityArrow.class.getDeclaredField("inGround");
			f.setAccessible(true);		
			inGround = (Boolean) f.get(this);
		} catch (Exception e) {
			e.printStackTrace();
		}	

		return inGround;		
	}

	@Override
	public void j_() {
		
		if (this.arrow != null) {
			Arrow a = (Arrow) this.getBukkitEntity();
			this.arrow.onTick((Player) a.getShooter(), this);
		}
			
	    super.j_();
	}
	
	@Override
	public void c_(EntityHuman entityhuman) {			
		if (!this.canPickup)
			return;

	    if ((!this.world.isStatic) && (this.inGround()) && (this.shake <= 0)) {	
	    	ItemStack ist = new ItemStack(Item.ARROW);
	      		
	    	org.bukkit.inventory.ItemStack is = null;
	      
	    	if (this.arrow != null) {
	    		is = this.arrow.getArrowDrop();
	    		ist = CraftItemStack.asNMSCopy(is);
	    	}    
	      
	    	if ((this.fromPlayer == 1) && (this.shake <= 0) && (entityhuman.inventory.canHold(ist) > 0)) {
	    	  	EntityItem i = new EntityItem(this.world, this.locX, this.locY, this.locZ, ist);

	        	PlayerPickupItemEvent e = new PlayerPickupItemEvent((Player)entityhuman.getBukkitEntity(), new CraftItem(this.world.getServer(), this, i), 0);
	        	this.world.getServer().getPluginManager().callEvent(e);

	        	if (e.isCancelled()) {
	        		return;
	        	}
	    	}

	    	boolean flag = (this.fromPlayer == 1) || ((this.fromPlayer == 2) && (entityhuman.abilities.canInstantlyBuild));

	      	if ((this.fromPlayer == 1) && (!entityhuman.inventory.pickup(ist))) {
	    	  	flag = false;
	      	}

	      	if (flag) {
	    	  		this.world.makeSound(this, "random.pop", 0.2F, ((this.random.nextFloat() - this.random.nextFloat()) * 0.7F + 1.0F) * 2.0F);
	    	  		entityhuman.receive(this, 1);
	        		this.die();
	      		}
	    	}
	  }
}