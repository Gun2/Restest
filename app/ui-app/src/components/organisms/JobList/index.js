import React, {useEffect, useMemo, useReducer, useState} from 'react';
import styled from "styled-components";
import axios from "axios";
import JobRow from "../../molecules/JobRow";
import JobContent from "../../molecules/JobContent";

const Box = styled.div`
`
const List = styled.div`
    display: flex;
    flex-direction : column;
    gap:5px;
    padding 10px;
`


function JobList({
                     data,
                     getData,
                     onCheckCallback = () => {
                     },
                     checkedIdSet: checkedIdSet = new Set(),
                     hideCheckBox,
                     readonly
                 },
) {

    return (
        <Box>
            <List>
                {
                    data.map((d, i) => {
                        return (
                            <JobRow
                                hideCheckBox={hideCheckBox}
                                checkSet={checkedIdSet}
                                title={d.title}
                                data={d}
                                onSaveCallback={() => {
                                    getData();
                                }}
                                onDeleteCallback={() => {
                                    getData();
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