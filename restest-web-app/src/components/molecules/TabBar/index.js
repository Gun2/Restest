import React, {useCallback, useState} from 'react';
import styled from "styled-components";
import Tab from "../../atoms/Tab";

const Box = styled.div`
    display : flex;
    flex-direction : column;
`;
const Head = styled.div`
    ${({theme}) => theme.flex.startCenter});
    gap : 1px;
`;

const Content = styled.div`

`

function TabBar({tabData = []}) {
    const [activeTab , setActiveTab] = useState(0);
    const onTab = useCallback((key) => {
        setActiveTab(key);
    }, []);

    return (
        <Box>
            <Head>
                {tabData.map((data, i) => (
                    <Tab onTab={onTab} key={i} item={i} active={activeTab==i}>{data.key}</Tab>
                ))}
            </Head>
            <Content>
                {tabData[activeTab].content}
            </Content>
        </Box>
    );
}

export default TabBar;