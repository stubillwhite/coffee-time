import React from 'react';
import Version from './components/Version';
import coffeetime from './apis/CoffeeTime';

class App extends React.Component {

  state = { serverVersion: null, clientVersion: null }

  readServerVersion = async (term) => {
    const response = await coffeetime.get('/version');

    this.setState({
      serverVersion: response.data,
      clientVersion: "1.2.3"
    });
  };

  componentDidMount() {
    this.readServerVersion();
  }

  render() {
    return (
      <div className="ui container">
        <h2>Coffee Time!</h2>
        <div>
          <Version serverVersion={this.state.serverVersion} clientVersion={this.state.clientVersion} />
        </div>
      </div>
    );
  }
}

export default App;
