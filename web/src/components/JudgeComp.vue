<template>
    <div>
        <div class="answer-select-div">
            <ul class="answer-select-ul" @click="changeAnswer(1)">
                <span class="round-span right-ul"></span> &nbsp;正确
            </ul>
            <ul class="answer-select-ul" @click="changeAnswer(0)">
                <span class="round-span fault-ul"></span> &nbsp;错误
            </ul>
        </div>
    </div>
</template>

<script lang="ts" setup>
import { ref, defineEmits } from 'vue';

const yourSelect = ref<boolean>();// eslint-disable-next-line
let background_color = '';
const emit = defineEmits(['updateJudge'])

const getCssColor = (val:number)=>{
    if(typeof yourSelect.value === 'undefined') return 'transparent';
    if(yourSelect.value === false)  return val === 1 ? 'transparent' : '#FFA500';
    else                            return val === 1 ? '#FFA500' : 'transparent';
}

function changeAnswer(val:number) {
    if(val === 1)   yourSelect.value = true;
    else            yourSelect.value = false;
    emit('updateJudge', yourSelect.value);
}
</script>

<style scoped>
.round-span {
    display: inline-block;
    border: 2px solid;
    height: 10px;
    width: 10px;
    border-radius: 50%;
}
.right-ul {
    background-color: v-bind(getCssColor(1)) !important;
}
.fault-ul {
    background-color: v-bind(getCssColor(0)) !important;
}
.answer-select-div {
    display: flex;
    direction: row;
}
.answer-select-ul:hover {
    cursor: pointer;
}
</style>