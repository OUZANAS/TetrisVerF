package Mino;

import java.awt.Color;
import java.awt.Graphics2D;

import Main.GamePanel;
import Main.KeyHandler;
import Main.PlayManager;

public class Mino {
    public Block b[] = new Block[4];
    public Block tempB[] = new Block[4];
    int autoDropCounter = 0;
    public int direction = 1; // there are 4 directions (1/2/3/4)
    boolean leftCollision, rightCollision, bottomCollision;
    public boolean active = true;
    public boolean deactivating;
    int deactivateCounter;

    public void create(Color c) {
        b[0] = new Block(c);
        b[1] = new Block(c);
        b[2] = new Block(c);
        b[3] = new Block(c);
        tempB[0] = new Block(c);
        tempB[1] = new Block(c);
        tempB[2] = new Block(c);
        tempB[3] = new Block(c);
    }

    public void setXY(int x, int y) {}

    public void updateXY(int direction) {
        checkRotationCollision();

        if (!leftCollision && !rightCollision && !bottomCollision) {
            this.direction = direction;
            for (int i = 0; i < 4; i++) {
                b[i].x = tempB[i].x;
                b[i].y = tempB[i].y;
            }
        }
    }

    public void getDirection1() {}
    public void getDirection2() {}
    public void getDirection3() {}
    public void getDirection4() {}

    public void checkMovementCollision() {
        leftCollision = false;
        rightCollision = false;
        bottomCollision = false;
        
     // check static block collision
        checkStaticsBlockCollision();

        for (int i = 0; i < b.length; i++) {
            if (b[i].x <= PlayManager.left_x) {
                leftCollision = true;
            }
            if (b[i].x + Block.SIZE >= PlayManager.right_x) {
                rightCollision = true;
            }
            if (b[i].y + Block.SIZE >= PlayManager.bottom_y) {
                bottomCollision = true;
            }
        }
    }

    public void checkRotationCollision() {
        leftCollision = false;
        rightCollision = false;
        bottomCollision = false;
        
        // check static block collision
        checkStaticsBlockCollision();

        //check frame Collision
        for (int i = 0; i < tempB.length; i++) {
            if (tempB[i].x < PlayManager.left_x) {
                leftCollision = true;
            }
            if (tempB[i].x + Block.SIZE > PlayManager.right_x) {
                rightCollision = true;
            }
            if (tempB[i].y + Block.SIZE > PlayManager.bottom_y) {
                bottomCollision = true;
            }
        }
    }

    private void checkStaticsBlockCollision() {
    	for(int i =0; i< PlayManager.staticBlocks.size();i++) {
    		
    		int targetX = PlayManager.staticBlocks.get(i).x;
    		int targetY = PlayManager.staticBlocks.get(i).y;
    		
    		//check down
    		for(int ii= 0;ii<b.length;ii++) {
    			if(b[ii].y + Block.SIZE == targetY && b[ii].x == targetX) {
    				bottomCollision = true;
    			}
    		}
    		// check left 
    			for(int ii1= 0;ii1<b.length;ii1++) {
        			if(b[ii1].x + Block.SIZE == targetX && b[ii1].y == targetY) {
        				leftCollision = true;
        			}
    			}
    			for(int ii2= 0;ii2<b.length;ii2++) {
        			if(b[ii2].x + Block.SIZE == targetX && b[ii2].y == targetY) {
        				rightCollision = true;
        			}
    			}
    	}
    }
    			
    public void update() {
    	if(deactivating) {
    		deactivating();
    	}
    	
    	//move the mino
        if (KeyHandler.upPressed) {
            direction++;
            if (direction > 4) direction = 1;

            switch (direction) {
                case 1: getDirection1(); break;
                case 2: getDirection2(); break;
                case 3: getDirection3(); break;
                case 4: getDirection4(); break;
            }

            KeyHandler.upPressed = false;
            GamePanel.se.play(3, false);
            
        }

        checkMovementCollision();

        if (KeyHandler.downPressed) {
            if (!bottomCollision) {
                for (int i = 0; i < 4; i++) {
                    b[i].y += Block.SIZE;
                }
                autoDropCounter = 0;
                checkMovementCollision(); // vérifie après déplacement
            }
            KeyHandler.downPressed = false;
        }

        if (KeyHandler.leftPressed) {
            if (!leftCollision) {
                for (int i = 0; i < 4; i++) {
                    b[i].x -= Block.SIZE;
                }
            }
            KeyHandler.leftPressed = false;
        }

        if (KeyHandler.rightPressed) {
            if (!rightCollision) {
                for (int i = 0; i < 4; i++) {
                    b[i].x += Block.SIZE;
                }
            }
            KeyHandler.rightPressed = false;
        }

        if (bottomCollision) {
        	if(deactivating == false) {
        		 GamePanel.se.play(4,false);
        	}
        	 deactivating = true;
        } 
        else {
            autoDropCounter++;
            if (autoDropCounter == PlayManager.dropInterval) {
                for (int i = 0; i < 4; i++) {
                    b[i].y += Block.SIZE;
                }
                autoDropCounter = 0;
                checkMovementCollision(); // vérifie après déplacement automatique
                if (bottomCollision) {
                    active = false;
                }
            }
        }
    }
    
    private void deactivating() {
    	deactivateCounter++;
    	// wait 45 frame until diactivate
    	if(deactivateCounter == 45) {
    		deactivateCounter = 0;
    		checkMovementCollision(); // check if the bottom is still hitting
    		
    		//if the bottom is still hitting after 45 frames,deactivate the mino
    		if(bottomCollision) {
    			active = false;
    		}
    		
    	}
    }

    public void draw(Graphics2D q2) {
        int margin = 2;
        for (int i = 0; i < 4; i++) {
            q2.setColor(b[i].c);
            q2.fillRect(b[i].x + margin, b[i].y + margin, Block.SIZE - (margin * 2), Block.SIZE - (margin * 2));
        }
    }
}



