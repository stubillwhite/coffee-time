import React from 'react';

const Version = ({ serverVersion, clientVersion }) => {
    return (
        <div>
            <div>
                Server: {serverVersion}
            </div>
            <div>
                Client: {clientVersion}
            </div>
        </div>
    );
};

export default Version;