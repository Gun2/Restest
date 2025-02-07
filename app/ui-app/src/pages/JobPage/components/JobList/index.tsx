import React from 'react';
import styled from "styled-components";
import JobRow from "../JobRow";
import {useReadAllQuery} from "modules/job";
import {Job} from "types/job.types";

const Box = styled.div`
`
const List = styled.div`
    display: flex;
    flex-direction : column;
    gap:5px;
    padding: 10px;
`

type JobListProps = {
    //job 리스트 체크 시 callback
    onCheckCallback?: (checkedIdSet: Set<number>) => void;
    //체크된 job의 id
    checkedIdSet?: Set<Job["id"]>;
    //체크박스 숨김
    hideCheckBox?: boolean;
    //읽기모드
    readonly?: boolean
}
function JobList(
    {
        onCheckCallback = () => {},
        checkedIdSet = new Set(),
        hideCheckBox,
        readonly
    }: JobListProps,
) {
    const {data, error, isLoading, refetch} = useReadAllQuery();

    return (
        <Box>
            <List>
                {
                    data && data.data.map((d, i) => {
                        return (
                            <JobRow
                                hideCheckBox={hideCheckBox}
                                checkSet={checkedIdSet}
                                title={d.title}
                                data={d}
                                onSaveCallback={() => {
                                    refetch();
                                }}
                                onDeleteCallback={() => {
                                    refetch();
                                }}
                                key={d.id}
                                onCheckCallback={(e, key) => {
                                    const checked = e.target.checked;
                                    var nextSet = new Set(checkedIdSet);
                                    if (checked) {
                                        nextSet.add(key);
                                    } else {
                                        nextSet.delete(key);
                                    }
                                    onCheckCallback(nextSet);
                                }}
                                readonly={readonly}
                            />
                        )
                    })
                }
            </List>
        </Box>
    );
}


export default JobList;