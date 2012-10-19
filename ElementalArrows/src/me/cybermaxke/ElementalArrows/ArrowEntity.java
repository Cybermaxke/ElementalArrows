package me.cybermaxke.ElementalArrows;

import me.cybermaxke.ElementalArrows.Materials.CustomArrowItem;

import net.minecraft.server.EntityHuman;
import net.minecraft.server.EntityLiving;
import net.minecraft.server.World;

public class ArrowEntity extends net.minecraft.server.EntityArrow {
	
	private CustomArrowItem arrow;
	private float power;	
	private int damage = 0;
	private int knockback = 0;
	private int fireticks = 0;
	
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
			this.b(this.d() + (double) amount * 0.5D + 0.5D);
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

	@Override
	public void b_(EntityHuman entityhuman) {
		//HumanEntity ent = entityhuman.getBukkitEntity();
		
        if (!this.world.isStatic && this.onGround && this.shake <= 0) {
            this.world.makeSound(this, "random.pop", 0.2F, ((this.random.nextFloat() - this.random.nextFloat()) * 0.7F + 1.0F) * 2.0F);
            //SpoutItemStack i = new SpoutItemStack(this.arrow);
            
            //SpoutPlayer p = SpoutManager.getPlayer((Player) ent);
    		
            this.die();
        }
    }
}