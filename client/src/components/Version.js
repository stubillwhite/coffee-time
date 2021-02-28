import React from 'react';

const Version = ({ serverVersion, clientVersion }) => {
    return (
        <div>
            <div>
                Server version {serverVersion}
            </div>
            <div>
                Client version {clientVersion}
            </div>
        </div>
    );
};

export default Version;