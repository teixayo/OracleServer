package me.teixayo.server.protocol.packet.client.play;

import lombok.Getter;
import me.teixayo.server.protocol.packet.client.ClientPacket;

import java.util.HashMap;

@Getter
public class EntityActionPacket extends ClientPacket {

    private int entityID;
    private Action actionID;
    private int actionParameter;
    /*ActionParameter only used by Horse Jump Boost, in which case it ranges from 0 to 100. In all other cases it is 0.*/

    @Override
    public void read() {
        entityID = readVarInt();
        actionID = Action.getAction(readVarInt());
        actionParameter = readVarInt();
    }


    public enum Action {
        Start_Sneaking(0),
        Stop_Sneaking(1),
        Leave_Bed(2),
        Start_Sprinting(3),
        Stop_Sprinting(4),
        Jump_With_horse(5),
        Open_Ridden_Horse_Inventory(6);

        private static final HashMap<Integer, Action> actions = new HashMap<>();

        static {
            for (Action action : values()) {
                actions.put(action.data, action);
            }
        }

        private final int data;

        Action(int data) {
            this.data = data;
        }

        public static Action getAction(int data) {
            return actions.get(data);
        }
    }
}
