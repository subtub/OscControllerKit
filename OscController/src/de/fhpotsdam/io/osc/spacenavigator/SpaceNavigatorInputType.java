package de.fhpotsdam.io.osc.spacenavigator;

public enum SpaceNavigatorInputType {
	BUTTON{
		public String toString(){
			return "button";
		}
	},
	ROTATION{
		public String toString(){
			return "rot";
		}
	},
	TRANSLATION{
		public String toString(){
			return "trans";
		}
	}
}
