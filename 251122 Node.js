// Node.js performance and stress test
/*
After my project ended, I wanted to do performance and stress test
that I couldn't do due to the limit of time. 
(Since our team has only three novice student 
who just first learned Node.js
so I have to concentrate on meet in the deadline as a team leader.)
So I edited autocannon-stress-test.js that the AI generated
and run the stress test, but it keeps responded with 400, not the 2xx that I expected.
(I've also wrote those trouble shooting in a personal notion,
https://pine-fibula-aee.notion.site/TrShot-How-to-do-Perform-Stress-Testing-in-Node-js-2b0fc892a16f8072a886dae68e98cc7a?pvs=74
too.)
And this was the file that I've been used.
*/

const autocannon = require('autocannon');
const { Writable } = require('stream');

const URL_TO_TEST = 'https://nb06-seven-team4.onrender.com/groups';
const CONCURRENCY = 100;
const DURATION = 30;

// Base request body
const BASE_BODY = {
  "description": "11월도 운동해요",
  "image": "https://example.com/images/group_logo_A.png",
  "tags": ["Running"],
  "discordwebhookurl": "http://discord.gg/exercise_good",
  "discordserverinviteurl": "https://discord.gg/exercise_good",
  "goalNumber": 250
};

// Function to generate the unique request body
function bodyGenerator(requestIndex) {
    const uniqueGroupName = `Exercise_IS_FUN_Test_${requestIndex}`;
    const uniqueNickname = `GOOD_EXERCISE_NICE_${requestIndex}`;
    const uniquePassword = `good_exercise_nice_${requestIndex}`;
    
    return JSON.stringify({
        ...BASE_BODY,
        groupName: uniqueGroupName,
        nickname: uniqueNickname,
        password: uniquePassword
    });
}

const instance = autocannon({
    url: URL_TO_TEST,
    method: 'POST',
    connections: 1,
    duration: 1,
    headers: {
        'Content-Type': 'application/json'
    },
    setupRequest: (req, i) => {
        req.body = bodyGenerator(i);
        return req;
    },
    setupClient: (client) => {
        client.on('response', (statusCode, resBytes, responseTime, headers, body) => {
            // Check if the status code is NOT in the 2xx range
            if (statusCode < 200 || statusCode >= 300) {
                // Log the status code and a small portion of the response body for debugging
                const bodyText = body ? body.toString('utf8').substring(0, 100) : 'No body';
                console.error(`\n🚨 Error Status Code: ${statusCode}. Response Snippet: ${bodyText}...`);
            }
        });
    },
    // The stdout: new Writable(...) setting 
    // ensures you only see the clean, structured output you decided to print.
    stdout: new Writable({
        write: (chunk, encoding, callback) => {
            // Do nothing, effectively silencing stdout from autocannon core
            callback();
        }
    })
}, (err, result) => {
    if (err) {
        console.error('Autocannon error:', err);
    }
    // Print only the summary results after the test is done
    console.log('\n--- AUTOCANNON SUMMARY RESULTS ---');
    console.log(autocannon.printResult(result));
});

instance.on('done', () => {
    console.log(`\nStress test complete. Target URL: ${URL_TO_TEST}`);
});

// Use autocannon.track without renderResults to avoid double printing the summary
autocannon.track(instance, { renderLatencyTable: false, renderResults: false });

/*
According to this JS file, the backend server(which I deployed with Render)
keeps showing '🚨 Error Status Code: 400. Response Snippet: No Body...'.
I think the BASE_BODY which doesn't have groupName, nickname and password is the problem.
Following my supposition, using one of JS's feature, 'HOISTING', to solve these problem.

e.g.
// Base request body
const BASE_BODY = {
  "groupName": uniqueGroupName,
  "description": "11월도 운동해요",
  "nickname": uniqueNickname,
  "password": uniquePassword,
  "image": "https://example.com/images/group_logo_A.png",
  "tags": ["Running"],
  "discordwebhookurl": "http://discord.gg/exercise_good",
  "discordserverinviteurl": "https://discord.gg/exercise_good",
  "goalNumber": 250
};

// Function to generate the unique request body
function bodyGenerator(requestIndex) {
    const uniqueGroupName = `Exercise_IS_FUN_Test_${requestIndex}`;
    const uniqueNickname = `GOOD_EXERCISE_NICE_${requestIndex}`;
    const uniquePassword = `good_exercise_nice_${requestIndex}`;
    
    return JSON.stringify({
        ...BASE_BODY,
        groupName: uniqueGroupName,
        nickname: uniqueNickname,
        password: uniquePassword
    });
}

Today's trouble-shooting:
https://pine-fibula-aee.notion.site/TrShot-Solving-400-error-occurred-in-stress-test-2b4fc892a16f80c1a20dfa99f5300129
*/
