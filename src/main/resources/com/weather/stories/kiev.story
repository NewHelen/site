
Scenario: Basic test Kiev

When search for <good>
Then choose the desired result
When get text max temperature C
And get text min temperature C
Then compare min and max C
When chose temperature F
Then get text max temperature F
When get text min temperature F
Then compare min and max F

Examples:
|good|
|Kiev|
