<template>
    <el-card class="el-card">
        <span class="span-pure-text">试卷标题：</span><span style="color: blue;">{{ handleTitle(props.data_checkedPaper.testpaper_title) }}</span><br>
        <span class="span-pure-text">开始时间：</span><span style="color: blue;">{{ props.data_checkedPaper.begin_time }}</span><br>
        <span class="span-pure-text">结束时间：</span><span style="color: blue;">{{ props.data_checkedPaper.end_time }}</span><br>
        <span class="span-pure-text">&nbsp;</span><br>
        <div style="display: flex; flex-direction: row-reverse;">
            <slot name="button"></slot>
        </div>
    </el-card>
</template>

<script lang="ts" setup>
import { defineProps } from 'vue';
function handleTitle(title:string)  {
    const begin = title.indexOf('}');
    return title.substring(begin+1, title.length);
}
const props = defineProps({
    data_checkedPaper: { 
        type: Object,
        required: true
    }
});
</script>

<style scoped lang="scss">
.el-card {
    display: inline-block;
    height: 200px;
    width: 350px;
    margin: 10px 10px;
}
.span-pure-text{
    letter-spacing: 2px;
}
</style>