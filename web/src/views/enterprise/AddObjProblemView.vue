<template>
    <el-form class="el-form">
        <br>
        <el-row>
            <el-col :span="2" class="el-col-text-pure">试题类型：</el-col>
            <el-col :span="6">
                <el-select class="el-select" v-model="problemType">
                    <el-option label="选择" value="select" />
                    <el-option label="判断" value="judge" />
                    <el-option label="填空" value="filling" />
                </el-select>
            </el-col>
        </el-row>

        <br>
        <el-row>
            <el-col :span="2" class="el-col-text-pure">标&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;题：</el-col>
            <el-col :span="8">
                <el-input v-model="title" placeholder="标题" clearable />
            </el-col>
        </el-row>
        
        <br>
        <el-row class="el-row-group">
            <el-col :span="2" class="el-col-text-pure">分&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;组：</el-col>
            <el-select class="el-select" v-model="groupSelect" placeholder="Select">
                <el-option :label="i" :value="i" v-for="(i,index) in groupStore.groupList" :key="index" />
            </el-select>
            <el-tooltip effect="dark" content="添加一个分组" placement="right-start">
                <el-icon class="el-icon" size="20"><CirclePlus @click="addNewGroup" /></el-icon>
            </el-tooltip>
        </el-row>

        <br>
        <el-row>
            <el-col :span="2" class="el-col-text-pure-descript">题目描述：</el-col>
            <el-col :span="16">
                <el-input class="el-input-descript" v-model="description" type="textarea" placeholder="题目描述" :autosize="{ minRows: 5, maxRows: 100 }" clearable />            
            </el-col>
        </el-row>
        
        <br>
        <el-row v-if="problemType==='select'">
            <el-col :span="2" class="el-col-text-pure">选项个数：</el-col>
            <el-select class="el-select" v-model="select_count">
                <el-option v-for="index in number" :key="index" :label="index" :value="index"  />
            </el-select>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <el-col :span="2" class="el-col-text-pure">正确答案：</el-col>
            <el-select class="el-select" v-model="right_select">
                <el-option v-for="index in select_count" :key="index" :label="String.fromCharCode(index+64)" :value="String.fromCharCode(index+64)"  />
            </el-select>
        </el-row>
        <el-row v-if="problemType==='judge'">
            <el-col :span="2" class="el-col-text-pure">正确答案：</el-col>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <el-radio-group v-model="right_select">
                <el-radio label="true">√</el-radio>
                <el-radio label="false">×</el-radio>
            </el-radio-group>
        </el-row>
        <el-row v-if="problemType==='filling'">
            <el-col :span="2" class="el-col-text-pure">正确答案：</el-col>
            <el-col :span="8">
                <el-input v-model="right_select" placeholder="答案" clearable />
            </el-col>
        </el-row>

        <br>
        <el-row style="display: flex; align-items: center;">
            <el-col class="el-col-text-pure" :span="2">难易程度：</el-col>
            <el-col :span="13">
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <el-radio-group v-model="radioSelectRank">
                    <el-radio label="noSet">暂不设置</el-radio>
                    <el-radio label="easy">容易</el-radio>
                    <el-radio label="average">一般</el-radio>
                    <el-radio label="difficult">难</el-radio>
                </el-radio-group>
            </el-col>
        </el-row>

        <br>
        <el-row>
            <el-col :span="2" class="el-col-text-pure">批改类型：</el-col>
            <el-col :span="13">
                <el-select v-model="checkSelect" disabled>
                    <el-option label="自动批改" value="mechine"  />
                    <el-option label="人工批改" value="human"  />
                </el-select>
            </el-col>
        </el-row>

        <br>
        <el-row v-if="problemType==='select'">
            <el-col :span="2" style="display: flex; justify-content: right; margin-top: 3px;">选&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;项：</el-col>
            <el-col :span="22">
                <el-input v-for="index in select_count" :key="index" v-model="selectAnswer[index]" :placeholder="String.fromCharCode(64+index)" style="width: 180px; margin-right: 12px; margin-bottom: 5px;" clearable/>
            </el-col>
        </el-row>
        <div style="margin-left: 770px; margin-top: 20px;">
            <el-button type="primary" @click="submit">提交</el-button>
        </div>
    </el-form>
    <AddingGroup ref="childRef"/>
</template>

<script lang="ts" setup>
import { ref } from 'vue';
import { CirclePlus } from '@element-plus/icons-vue'
import { useUserStore } from '@/store/user';
import $ from 'jquery';
import { useGroupStore } from '@/store/group';
import AddingGroup from '@/components/AddingGroup.vue';
import { ElMessage } from "element-plus";

const userStore = useUserStore();
const groupStore = useGroupStore();

const number = 8;

const problemType = ref('judge');
const title = ref('');
const groupSelect = ref('默认分组');
const select_count = ref(4);
const description = ref('');
const radioSelectRank = ref('noSet');
const checkSelect = 'mechine';
const right_select = ref('');
const selectAnswer = ref([]);

const childRef = ref();

groupStore.getAllGroups();

function addNewGroup() {
    childRef.value.changeDialogVisable();
}

function submit(){
    $.ajax({
        url: 'http://localhost:3000/problem/submit/objectivity/',
        type: 'post',
        headers: {
            Authorization: "Bearer " + userStore.token,
        },
        data:{
            'problemType' :problemType.value,
            'title': title.value,
            'groupSelect': groupSelect.value,
            'description': description.value,
            'rightAnswer': right_select.value,
            'radioSelectRank': radioSelectRank.value,
            'checkSelect': checkSelect,
            'appendix': selectAnswer.value
        },
        success: (resp:string)=>{
            if(JSON.parse(resp).error_message === 'success'){
                title.value = description.value = '';
                ElMessage({message: "成功添加一个试题", type: 'success',})
                selectAnswer.value = [];
            }
        },
        error(){
            ElMessage.error("添加失败");
        }
    })
}

</script>

<style scoped>
.el-form{
    margin-top: 10px;
    margin-left: 30px;
}
.el-col-text-pure{
    display: flex;
    justify-content: right;
    align-items: center;
}
.el-select{
    width: 200px;
}
.el-icon{
    margin-left: 9px;
    color: blue;
}
.el-icon:hover{
    cursor: pointer;
}
.el-row-group{
    display: flex;
    align-items: center;
}
.el-col-text-pure-descript{
    display: flex;
    justify-content: right;
    align-items: top;
}
.el-input-descript{
    min-height: 50px;
}
</style>