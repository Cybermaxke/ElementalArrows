/**
 * 
 * This software is part of the ElementalArrows
 * 
 * ElementalArrows is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or 
 * any later version.
 * 
 * ElementalArrows is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with ElementalArrows. If not, see <http://www.gnu.org/licenses/>.
 * 
 */
package me.cybermaxke.elementalarrows.spout.api.utils;

import java.util.ArrayList;
import java.util.List;

import org.spout.api.geo.discrete.Point;

public class MathUtils {

	private MathUtils() {

	}

	/**
	 * Gets the points on the circle around the position in a specific plane.
	 * @param location
	 * @param range
	 * @return points
	 */
	public static List<Point> getCirclePoints(Point position, Plane plane, float range) {
		return getCirclePoints(position, plane, range, 360);
	}

	/**
	 * Gets the points on the circle around the position in a specific plane.
	 * @param location
	 * @param range
	 * @param amount
	 * @return points
	 */
	public static List<Point> getCirclePoints(Point position, Plane plane, float range, int amount) {
		return getCirclePoints0(position, plane, range, amount, 0);
	}

	/**
	 * Gets the points on the circle around the position in a specific plane.
	 * @param location
	 * @param range
	 * @param amount
	 * @param startrotation
	 * @return points
	 */
	private static List<Point> getCirclePoints0(Point position, Plane plane, float range, int amount, int startrotation) {
		List<Point> points = new ArrayList<Point>();
		int j = 360 / amount;

		for (int i = 0; i < (360 / j); i++) {
			int d = (i * j) + startrotation;

			float x = (float) (range * Math.cos(Math.toRadians(d)));
			float y = (float) (range * Math.sin(Math.toRadians(d)));

			switch (plane) {
				case XZ:
					points.add(new Point(position.getWorld(), position.getX() + x, position.getY(), position.getZ() + y));
					break;
				case YZ:
					points.add(new Point(position.getWorld(), position.getX(), position.getY() + y, position.getZ() + x));
					break;
				case XY:
				default:
					points.add(new Point(position.getWorld(), position.getX() + x, position.getY() + y, position.getZ()));
			}
		}

		return points;
	}

	/**
	 * Gets the points of a line bewteen two points.
	 * @param start
	 * @param end
	 * @param amount
	 * @return points
	 */
	public static List<Point> getLinePoints(Point start, Point end, int amount) {
		List<Point> points = new ArrayList<Point>();

		float k = start.getX();
		float j = start.getY();
		float n = start.getZ();

		float l = end.getX() - k;
		float h = end.getY() - j;
		float w = end.getZ() - n;

		float f1 = l / amount;
		float f2 = h / amount;
		float f3 = w / amount;

		for (int i = 0; i < amount; i++) {
			points.add(new Point(start.getWorld(), k + f1 * i, j + f2 * i, n + f3 * i));
		}

		return points;
	}

	/**
	 * Gets the points of a shape around the position in the specific plane.
	 * @param position
	 * @param plane
	 * @param range
	 * @param corners
	 * @param amount
	 * @return points
	 */
	public static List<Point> getShapePoints(Point position, Plane plane, float range, int corners, int amount) {
		return getShapePoints(position, plane, range, corners, amount, 0);
	}

	/**
	 * Gets the points of a shape around the position in the specific plane.
	 * @param position
	 * @param plane
	 * @param range
	 * @param corners
	 * @param amount
	 * @param startrotation
	 * @return points
	 */
	public static List<Point> getShapePoints(Point position, Plane plane, float range, int corners, int amount, int startrotation) {
		List<Point> points = new ArrayList<Point>();
		List<Point> points2 = getCirclePoints0(position, plane, range, corners, startrotation);
		points.addAll(points2);

		int a = (amount - corners) / corners;

		Point start = null;
		Point last = null;
		for (int i = 0; i < points2.size(); i++) {
			if (start == null) {
				start = points2.get(i);
				last = start;
				continue;
			}

			Point point = points2.get(i);
			points.addAll(getLinePoints(last, point, a));
			last = point;
		}

		if (start != null && last != null) {
			points.addAll(getLinePoints(last, start, a));
		}

		return points;
	}

	/**
	 * Gets the points of a star around the position in the specific plane.
	 * @param position
	 * @param plane
	 * @param range
	 * @param amount
	 * @return points
	 */
	public static List<Point> getStarPoints(Point position, Plane plane, float range, int amount) {
		return getStarPoints(position, plane, range, amount, 0);
	}

	/**
	 * Gets the points of a star around the position in the specific plane.
	 * @param position
	 * @param plane
	 * @param range
	 * @param amount
	 * @param startrotation
	 * @return points
	 */
	public static List<Point> getStarPoints(Point position, Plane plane, float range, int amount, int startrotation) {
		List<Point> points = getCirclePoints0(position, plane, range, 5, startrotation);
		int a = (amount - 5) / 5;

		Point p1 = points.get(0);
		Point p2 = points.get(1);
		Point p3 = points.get(2);
		Point p4 = points.get(3);
		Point p5 = points.get(4);

		points.addAll(getLinePoints(p1, p3, a));
		points.addAll(getLinePoints(p1, p4, a));
		points.addAll(getLinePoints(p2, p4, a));
		points.addAll(getLinePoints(p2, p5, a));
		points.addAll(getLinePoints(p3, p5, a));

		return points;
	}

	/**
	 * Gets the points of a octahedron around the position in the specific plane.
	 * @param position
	 * @param plane
	 * @param range
	 * @param height
	 * @param amount
	 * @return points
	 */
	public static List<Point> getOctahedronPoints(Point position, Plane plane, float range, float height, int amount) {
		return getOctahedronPoints(position, plane, range, height, amount, 0);
	}

	/**
	 * Gets the points of a octahedron around the position in the specific plane.
	 * @param position
	 * @param plane
	 * @param range
	 * @param height
	 * @param amount
	 * @param startrotation
	 * @return points
	 */
	public static List<Point> getOctahedronPoints(Point position, Plane plane, float range, float height, int amount, int startrotation) {
		List<Point> points = getCirclePoints0(position, plane, range, 4, startrotation);
		int a = (amount - 4) / 12;

		Point p1 = points.get(0);
		Point p2 = points.get(1);
		Point p3 = points.get(2);
		Point p4 = points.get(3);

		Point p5 = null;
		Point p6 = null;

		switch (plane) {
			case XZ:
				p5 = position.add(0.0F, height / 2, 0.0F);
				p6 = position.add(0.0F, -(height / 2), 0.0F);
				break;
			case YZ:
				p5 = position.add(height / 2, 0.0F, 0.0F);
				p6 = position.add(-(height / 2), 0.0F, 0.0F);
				break;
			case XY:
			default:
				p5 = position.add(0.0F, 0.0F, height / 2);
				p6 = position.add(0.0F, 0.0F, -(height / 2));
				break;
		}

		points.addAll(getLinePoints(p1, p2, a));
		points.addAll(getLinePoints(p2, p3, a));
		points.addAll(getLinePoints(p3, p4, a));
		points.addAll(getLinePoints(p4, p1, a));

		points.addAll(getLinePoints(p1, p5, a));
		points.addAll(getLinePoints(p2, p5, a));
		points.addAll(getLinePoints(p3, p5, a));
		points.addAll(getLinePoints(p4, p5, a));

		points.addAll(getLinePoints(p1, p6, a));
		points.addAll(getLinePoints(p2, p6, a));
		points.addAll(getLinePoints(p3, p6, a));
		points.addAll(getLinePoints(p4, p6, a));

		return points;
	}

	/**
	 * Gets the points of a pyramid around the position in the specific plane.
	 * @param position
	 * @param plane
	 * @param range
	 * @param height
	 * @param amount
	 * @return points
	 */
	public static List<Point> getPyramidPoints(Point position, Plane plane, float range, float height, int amount) {
		return getOctahedronPoints(position, plane, range, height, amount, 0);
	}

	/**
	 * Gets the points of a pyramid around the position in the specific plane.
	 * @param position
	 * @param plane
	 * @param range
	 * @param height
	 * @param amount
	 * @param startrotation
	 * @return points
	 */
	public static List<Point> getPyramidPoints(Point position, Plane plane, float range, float height, int amount, int startrotation) {
		List<Point> points = getCirclePoints0(position, plane, range, 4, startrotation);
		int a = (amount - 4) / 12;

		Point p1 = points.get(0);
		Point p2 = points.get(1);
		Point p3 = points.get(2);
		Point p4 = points.get(3);

		Point p5 = null;

		switch (plane) {
			case XZ:
				p5 = position.add(0.0F, height / 2, 0.0F);
				break;
			case YZ:
				p5 = position.add(height / 2, 0.0F, 0.0F);
				break;
			case XY:
			default:
				p5 = position.add(0.0F, 0.0F, height / 2);
				break;
		}

		points.addAll(getLinePoints(p1, p2, a));
		points.addAll(getLinePoints(p2, p3, a));
		points.addAll(getLinePoints(p3, p4, a));
		points.addAll(getLinePoints(p4, p1, a));

		points.addAll(getLinePoints(p1, p5, a));
		points.addAll(getLinePoints(p2, p5, a));
		points.addAll(getLinePoints(p3, p5, a));
		points.addAll(getLinePoints(p4, p5, a));

		return points;
	}
}