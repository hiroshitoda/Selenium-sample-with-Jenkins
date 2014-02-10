Selenium-sample-with-Jenkins
============================

Jenkins で Selenium Grid 2 を動かすための簡易セットです。

必要な環境
========

* インターネットに接続されている環境 （ Maven が依存ライブラリーを取得します）
* git コマンドを実行できる環境
* JDK 1.6 以上がインストールされている環境

使い方
=====

1. bin の下の、 IEDriverServer または ChromeDriver の ZIP ファイルを展開して、PATH の通っているディレクトリーにコピーしてください。
2. Jenkins を起動してください。
    * Windows の場合: bin\run-jenkins.bat を実行。
    * Mac/Linux の場合: bin/run-jenkins.sh を実行。
3. http://localhost:8080/ にアクセスして、動かしたいジョブを実行してください。 JAVA_HOME を適切に指定すると、ジョブが実行されます。
4. 実行するコードは、ローカルの Git リポジトリーから取得する設定になっています。コードを変更する場合は、ローカルの Git リポジトリーに対して git commit すれば OK です。

構成ソフトのバージョン
==================

* Jenkins: 1.549
* Selenium: 2.39.0
* ChromeDriver: 2.9
* Maven: 3.1.1
