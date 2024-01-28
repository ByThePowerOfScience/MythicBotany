package mythicbotany.advancement;

import com.google.gson.JsonObject;
import mythicbotany.MythicBotany;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;

public class AlfRepairTrigger extends SimpleCriterionTrigger<AlfRepairTrigger.Instance> {

    public static final ResourceLocation ID = MythicBotany.getInstance().resource("alf_repair");
    
    @Nonnull
    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Nonnull
    @Override
    protected Instance createInstance(@Nonnull JsonObject json, @Nonnull ContextAwarePredicate entityPredicate, @Nonnull DeserializationContext context) {
        return new Instance(entityPredicate, ItemPredicate.fromJson(json.get("item")));
    }

    public void trigger(ServerPlayer player, ItemStack item) {
        this.trigger(player, (instance) -> instance.item.matches(item));
    }

    public static class Instance extends AbstractCriterionTriggerInstance {

        public final ItemPredicate item;

        public Instance(ItemPredicate item) {
            this(ContextAwarePredicate.ANY, item);
        }
        
        public Instance(ContextAwarePredicate player, ItemPredicate item) {
            super(AlfRepairTrigger.ID, player);
            this.item = item;
        }
        
        @Nonnull
        public JsonObject serializeToJson(@Nonnull SerializationContext conditions) {
            JsonObject json = super.serializeToJson(conditions);
            json.add("item", this.item.serializeToJson());
            return json;
        }
    }
}
