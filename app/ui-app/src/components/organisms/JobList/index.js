import React from 'react';
import styled from "styled-components";
import JobRow from "../../molecules/JobRow";
import {useReadAllQuery} from "../../../modules/job.ts";

const Box = styled.div`
`
const List = styled.div`
    display: flex;
    flex-direction : column;
    gap:5px;
    padding 10px;
`


function JobList({
                     onCheckCallback = () => {
                     },
                     checkedIdSet: checkedIdSet = new Set(),
                     hideCheckBox,
                     readonly
                 },
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
                                _key={d.id}
                                onCheckCallback={(e, key) => {
                                    const checked = e.target.checked;
                                    var nextSet = new Set([...checkedIdSet]);
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