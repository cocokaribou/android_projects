import 'package:flutter/cupertino.dart';
import 'package:test1/data/Post.dart';
import 'package:test1/data/PostRepository.dart';

import 'package:http/http.dart' as http;
import 'dart:convert';

class PostViewModel extends ChangeNotifier {
  final PostRepository repository;

  PostViewModel({required this.repository});

  List<Post> _posts = [];

  List<Post> get posts => _posts;

  bool _isLoading = false;

  bool get isLoading => _isLoading;

  Future<void> fetchPosts() async {
    _isLoading = true;
    notifyListeners();

    try {
      var url = Uri.parse("https://catfact.ninja/fact");
      var response = await http.get(url);

      if (response.statusCode == 200) {
        var responseData = json.decode(response.body);
        _posts.add(Post(content: responseData['fact']));
      } else {
        print('Request failed with status : ${response.statusCode}');
      }
    } catch (e) {
      print('Error occurred! $e');
    }

    _isLoading = false;
    notifyListeners();
  }

  void addPost(String content) async {
    _isLoading = true;
    notifyListeners();

    final newPost = Post(content: content);
    await repository.addPost(newPost);

    _posts.add(newPost);
    _isLoading = false;
    notifyListeners();
  }

  void clearPost() {
    _posts.clear();
    notifyListeners();
  }
}
