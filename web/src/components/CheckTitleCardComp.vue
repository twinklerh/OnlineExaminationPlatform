<template>
    <div :class="containerClass">
        <div class="title">
            <slot name="title"></slot>
        </div>
    </div>
</template>

<script lang="ts" setup>
import { defineProps, computed } from 'vue';
const props = defineProps({
    index: { type: Number,default: -1 }
});

const containerClass = computed(() => {
    return {
        container: true,
        'even-background': props.index % 2 === 0,
        'odd-background': props.index % 2 !== 0
    };
});
</script>

<style scoped>
.container {
    display: block;
    width: 100%;
    height: auto;
    padding: 10px 3px;
}

.container:hover {
    cursor: pointer;
}

div.title {
    display: flex;
    align-items: center;
    height: auto;
}

.even-background {
    background-color: #FAEBD7;
}

.odd-background {
    background-color: #FDF5E6;
}
</style>
