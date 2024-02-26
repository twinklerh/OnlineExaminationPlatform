import { ProblemInterface } from "@/store/problem";

export interface Option {
    key: number
    label: string
    initial: ProblemInterface
}

export function rawData_to_sourceData(problmeList: ProblemInterface[]): Option[] {
    const optionList: Option[] = [];
    problmeList.forEach((problem,index) => {
        optionList.push({
            label: problem.title,
            key: index,
            initial: problmeList[index],
        });
    })
    return optionList;
}

export function sortProblem(problemList: ProblemInterface[]) {
    problemList.sort((a,b) => {
        const titleA = a.title.toLowerCase();
        const titleB = b.title.toLowerCase();
        if (titleA < titleB) return -1;
        if (titleA > titleB) return 1;
        return 0;
    });
}
