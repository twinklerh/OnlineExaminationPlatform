<template>
    <div style="margin: 10px 10px">
        <div :data="group">
            <ul v-for="(item, index) in props.group" :key="index" class="selection-ul" @click="selectAction(index)">
                <span :class="{ 'selected-span': isSelected === index, 'unselected-span': isSelected !== index }">
                    {{ String.fromCharCode(index+65) }}
                </span>
                <span>&nbsp; {{ item }}</span>
            </ul>
        </div>
    </div>
</template>

<script lang="ts" setup>
import { ref } from 'vue';
import { defineProps, defineEmits } from 'vue';

const isSelected = ref<number | null>(null);

const props = defineProps({
    group: {
        type: Array as ()=> string[],
        default: () => ['1', '2', '3', '4'],
    }
})
const emit = defineEmits(['updateAnswer']);

function selectAction(index: number) {
    isSelected.value = index;
    emit('updateAnswer', isSelected.value)
}

</script>

<style scoped>
.selection-ul {
    display: inline-block;
}
.selection-ul:hover {
    cursor: pointer;
}
.selection-ul:hover .unselected-span{
    cursor: pointer;
    background-color: #F5DEB3;
}
.unselected-span {
    display: inline-flex;
    height: 22px;
    width: 22px;
    justify-content: center;
    align-items: center;
    border: 1px solid;
    border-radius: 50%;
}
.selected-span {
    display: inline-flex;
    height: 22px;
    width: 22px;
    justify-content: center;
    align-items: center;
    border: 1px solid;
    border-radius: 50%;
    background-color: #FFA500;
}
</style>
