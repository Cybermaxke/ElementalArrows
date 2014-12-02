/**
 * This file is part of ElementalArrows.
 * 
 * Copyright (c) 2014, Cybermaxke
 * 
 * ElementalArrows is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * ElementalArrows is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with ElementalArrows. If not, see <http://www.gnu.org/licenses/>.
 */
package me.cybermaxke.elementarrows.common.arrow;

import me.cybermaxke.elementarrows.common.arrow.event.EventEntityBuild;
import me.cybermaxke.elementarrows.common.arrow.event.EventEntityHitEntity;
import me.cybermaxke.elementarrows.common.arrow.event.EventEntityHitGround;
import me.cybermaxke.elementarrows.common.arrow.event.EventEntityShot;
import me.cybermaxke.elementarrows.common.arrow.event.EventInitialize;
import me.cybermaxke.elementarrows.common.arrow.event.EventEntityTick;

public interface ElementArrow {

	/**
	 * Gets the location of the icon texture.
	 * 
	 * @return the icon
	 */
	String getIcon();

	/**
	 * Gets the location of the entity texture.
	 * 
	 * @return the icon
	 */
	String getTexture();

	/**
	 * Gets the unlocalized name of the arrow.
	 * 
	 * @return the unlocalized name
	 */
	String getUnlocalizedName();

	/**
	 * Gets whether the item the glow effect has.
	 * 
	 * @return has effect
	 */
	boolean hasEffect();

	/**
	 * Handle the arrow initialize event.
	 * 
	 * @param event the event
	 */
	void handle(EventInitialize event);

	/**
	 * Handle the arrow tick event.
	 * 
	 * @param event the event
	 */
	void handle(EventEntityTick event);

	/**
	 * Handle the arrow build event.
	 * 
	 * @param event the event
	 */
	void handle(EventEntityBuild event);

	/**
	 * Handle the arrow shot event.
	 * 
	 * @param event the event
	 */
	void handle(EventEntityShot event);

	/**
	 * Handle the arrow hit ground event.
	 * 
	 * @param event the event
	 */
	void handle(EventEntityHitGround event);

	/**
	 * Handle the arrow hit entity event.
	 * 
	 * @param event the event
	 */
	void handle(EventEntityHitEntity event);

}