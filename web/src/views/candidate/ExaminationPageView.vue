<template>
    <div class="parent-div">
        <el-card class="el-card">
            <template #header>
                <div class="card-header">
                    <span class="exam-title-span">{{ displayTitle }}</span>
                </div>
            </template>
            <div v-for="(item,index) in list" :key="index">
                <div v-if="item.type === '选择'">{{ index+1 }}、
                    <span>{{ item.description }}</span>
                    <SelectionComp :group="item.appendix" @updateAnswer="updateAnswer(item.problemId, $event)"></SelectionComp>
                </div>
                <div v-else-if="item.type === '填空'">{{ index+1 }}、
                    <span>{{ item.description }}</span>
                    <br>
                    <el-row style="display: flex; align-items: center;">
                        <span style="margin-left: 25px; font-weight: 100;">你的答案：</span>
                        <el-input v-model="myAnswer_fill[item.problemId]" style="width: 275px;" placeholder="你的答案"/>
                    </el-row>
                </div>
                <div v-else-if="item.type === '判断'">{{ index+1 }}、
                    <span>{{ item.description }}</span><br>
                    <JudgeComp @updateJudge = "updateJudge(item.problemId, $event)" />
                </div>
                <div v-else>{{ index+1 }}、
                    <span>{{ item.description }}</span>
                    <el-input v-model="myAnswer_text[item.problemId+ 1]" type="textarea" placeholder="答：" />
                </div>
                <br>
            </div>
            <template #footer>
                <div class="submit-button">
                    <el-button type="primary" @click="submit">提交</el-button>
                </div>
                
            </template>
        </el-card>
    </div>
</template>

<script lang="ts" setup>
import { useUserStore } from '@/store/user';
import { useRoute, useRouter } from 'vue-router';
import $ from 'jquery';
import { ElMessage, ElMessageBox } from 'element-plus';
import { ref } from 'vue';
import SelectionComp from '@/components/SelectionComp.vue'
import JudgeComp from '@/components/JudgeComp.vue';
const route = useRoute();
const router = useRouter();
const testpaperTitle = decodeURIComponent(route.query.title as string);
const examId = decodeURIComponent(route.query.examId as string);
const displayTitle = testpaperTitle.slice(testpaperTitle.indexOf('}') + 1);
interface problem { problemId: number, description: string, type: string, appendix: string[] }
const list = ref<problem[]>([]);
const myAnswer_select = ref<Map<number, number>>(new Map<number, number>());
const myAnswer_judge = ref<Map<number, boolean>>(new Map<number, boolean>());
const myAnswer_fill = ref<string[]>([]);
const myAnswer_text = ref<string[]>([]);
$.ajax({
    url: 'http://127.0.0.1:3000/handout/testpaper/',
    type: 'post',
    headers: {
        Authorization: 'Bearer ' + useUserStore().token,
    },
    data: {
        testpaperTitle: testpaperTitle,
        examId: examId,
    },
    success: (result:string)=>{
        console.log(result);
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

function updateAnswer(problemId:number, selection:number) {
    myAnswer_select.value.set(problemId, selection);
}

function updateJudge(problemId:number, selection:boolean) {
    myAnswer_judge.value.set(problemId, selection);
}

function submit(){
    ElMessageBox.confirm('您确认要提交吗？', '提交试卷', {
        confirmButtonText: '确认',
        cancelButtonText: '取消',
        type: 'warning',
    }).then(()=>{
        let obj: { [key: string]: number } = {};
        myAnswer_select.value.forEach((value, key)=>{  obj[key] = value;   })
        let obj2: { [key: string]: boolean } = {};
        myAnswer_judge.value.forEach((value, key) => {  obj2[key] = value;  })
        let obj3: { [key: string]: string } = {};
        myAnswer_fill.value.forEach((value, key) => {   obj3[key] = value;  })
        let obj4: { [key: string]: string } = {};
        myAnswer_text.value.forEach((value, key)=>{   obj4[key] = value;    })
        $.ajax({
            url: 'http://127.0.0.1:3000/candidate/submit/exam/',
            type: 'post',
            headers: {
                Authorization: 'Bearer ' + useUserStore().token,
            },
            data: {
                examId: examId,
                testpaperTitle: testpaperTitle,
                myAnswer_select: JSON.stringify(obj),
                myAnswer_judge: JSON.stringify(obj2),
                myAnswer_fill: JSON.stringify(obj3),
                myAnswer_text: JSON.stringify(obj4),
            },
            success: (result:string)=>{
                const resp = JSON.parse(result);
                if(resp.error_message === 'success') {
                    ElMessageBox.alert('提交成功！', 'Tip', {
                        confirmButtonText: '确定',
                    });
                    router.push({name: 'exams'});
                } else ElMessage.error("提交失败");
            },
            error: ()=>{
                console.log("失败");
            }
        })
    }).catch(()=>{ return ;});
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
.submit-button {
    display: flex;
    flex-direction: row-reverse;
    margin-right: 45px;
}
</style>