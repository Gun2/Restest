import React from 'react';
import styled from 'styled-components';

const Box = styled.div`
    display:flex;
    height:100vh;
    gap:5px;
`;
const Menu = styled.div``;
const Content = styled.div`
    flex: 1;
    max-height:100%;
    overflow:auto;
`;
type LayoutTemplateProps = {
    menu: React.ReactNode;
    content: React.ReactNode;

}
function LayoutTemplate(
    {
        menu,
        content
    }: LayoutTemplateProps
) {
    return (
        <Box>
            <Menu>
                {menu}
            </Menu>
            <Content>
                {content}
            </Content>
        </Box>
    );
}

export default LayoutTemplate;