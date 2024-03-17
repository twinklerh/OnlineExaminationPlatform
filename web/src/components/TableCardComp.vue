<template>
    <el-card class="el-card">
        <span class="span-pure-text">试卷标题：</span><span style="color: blue;">{{ props.data_checkedPaper.testpaper_title }}</span><br>
        <span class="span-pure-text">开始时间：</span><span style="color: blue;">{{ props.data_checkedPaper.begin_time }}</span><br>
        <span class="span-pure-text">结束时间：</span><span style="color: blue;">{{ props.data_checkedPaper.end_time }}</span><br>
        <span class="span-pure-text">状态：</span><span style="color: red;">{{ msg }}</span><br>
        <div style="display: flex; flex-direction: row-reverse;">
            <slot name="button"></slot>
        </div>
    </el-card>
</template>

<script lang="ts" setup>
import { defineProps, ref } from 'vue';
const props = defineProps({
    data_checkedPaper: { 
        type: Object,
        required: true
    }
});
const msg = ref(props.data_checkedPaper.is_checked === false ? '未批改' : '已批改');
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