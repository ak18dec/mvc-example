<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta charset="utf-8">

        <link rel='stylesheet' type='text/css' href='/${artifactId}/webjars/semantic-ui/${semantic-ui.version}/dist/semantic.css'>


        <title>${dashboard.name}</title>
    </head>
    <body>
        <h2 class="ui center aligned icon header">
            <i class="circular feed icon"></i>
            ${dashboard.name}
        </h2>
        
        <div class="ui grid container">
            <div class="four wide column">
                <div class="ui card">
                    <div class="content">
                        <div class="right floated meta">14h</div>
                        <img class="ui avatar image" src="/images/avatar/large/elliot.jpg"> Elliot
                    </div>
                    <div class="image">
                        <img>
                    </div>
                    <div class="content">
                        <span class="right floated">
                            <i class="heart outline like icon"></i>
                            17 likes
                        </span>
                        <i class="comment icon"></i>
                        3 comments
                    </div>
                    <div class="extra content">
                        <div class="ui large transparent left icon input">
                            <i class="heart outline icon"></i>
                            <input placeholder="Add Comment..." type="text">
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <script type='text/javascript' src='/${artifactId}/webjars/jquery/${jquery.version}/dist/jquery.js'></script>
        <script type='text/javascript' src='/${artifactId}/webjars/semantic-ui/${semantic-ui.version}/dist/semantic.js'></script>
    </body>
</html>
