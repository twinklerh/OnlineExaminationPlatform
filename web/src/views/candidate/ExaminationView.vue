<template>
    <div class="parent-div">
        <el-card class="el-card">
            <span class="exam-title-span">{{ displayTitle }}</span>
            <el-divider></el-divider>
            <div v-for="(item,index) in list" :key="index">
                <div v-if="item.type === '选择'">{{ index+1 }}、
                    <span>{{ item.description }}</span>
                    <SelectionComp :group="item.appendix" @updateAnswer="updateAnswer(index, $event)"></SelectionComp>
                </div>
                <div v-else-if="item.type === '填空'">{{ index+1 }}、
                    <span>{{ item.description }}</span>
                </div>
                <div v-else-if="item.type === '判断'">{{ index+1 }}、
            <span>{{ item.description }}</span>
                </div>
                <div v-else>{{ index+1 }}、
                    <span>{{ item.description }}</span>
                </div>
            </div>
        </el-card>
    </div>

</template>

<script lang="ts" setup>
import { useUserStore } from '@/store/user';
import { useRoute } from 'vue-router';
import $ from 'jquery';
import { ElMessage } from 'element-plus';
import { ref } from 'vue';
import SelectionComp from '@/components/SelectionComp.vue'
const route = useRoute();
const testpaperTitle = decodeURIComponent(route.query.title as string);
const displayTitle = testpaperTitle.slice(testpaperTitle.indexOf('}') + 1);
interface problem { problemId: number, description: string, type: string, appendix: string[] }
const list = ref<problem[]>([]);
const myAnswer = ref<Map<number, string>>(new Map<number, string>());

$.ajax({
    url: 'http://127.0.0.1:3000/handout/testpaper/',
    type: 'post',
    headers: {
        Authorization: 'Bearer ' + useUserStore().token,
    },
    data: {
        testpaperTitle: testpaperTitle,
    },
    success: (result:string)=>{
        const resp: { problemId: number, description: string, type: string, appendix: string }[] = JSON.parse(result);
        resp.forEach((item)=>{
            if(item.type==='选择'){
                const appendixArr = JSON.parse(item.appendix);// 解析字符串为数组
                appendixArr.splice(0,1) // 移除数组中的 null 元素
                item.appendix = appendixArr;
            }
        });
        list.value = resp as unknown as problem[];
    },
    error: ()=>{
        ElMessage.error("error");
    }
})

function updateAnswer(index:number, selection:number) {
    myAnswer.value.set(index, String.fromCharCode(selection));
}
</script>

<style scoped>
.el-card{
    margin: 5px auto;
    width: 1080px;
    background-color: #F0FFFF;
    border-radius: 17px;
}
.exam-title-span{
    font-size: 24px;
    display: flex;
    justify-content: center;
    font-weight: bold;
    letter-spacing: 5px;
}
</style>